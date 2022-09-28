package com.mlongbo.sunflower.ioc;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 类扫描器
 * @author malongbo
 * @date 2014/12/29
 * @package com.mlongbo.sunflower.ioc
 */
public class Scanner {
    
    private ClassLoader classLoader = null;

    /**
     * 扫描包下面的类
     * @param packageName
     */
    public Set<Class<?>> scanPackage(String packageName) {
        classLoader = Thread.currentThread().getContextClassLoader();
        //是否循环搜索包
        boolean recursive = true;
        //存放扫描到的类
        Set<Class<?>> classes = new LinkedHashSet<>();

        //将包名转换为文件路径
        String packageDirName = packageName.replace('.', '/');

        try {
            Enumeration<URL> dirs = classLoader.getResources(packageDirName);
            
            while (dirs.hasMoreElements()) {
                URL url = dirs.nextElement();
                String protocol = url.getProtocol();
                if ("file".equals(protocol)) {
                    // 获取包的物理路径
                    String filePath = URLDecoder.decode(url.getFile(), StandardCharsets.UTF_8);
                    // 以文件的方式扫描整个包下的文件 并添加到集合中
                    findAndAddClassesInPackageByFile(packageName, filePath,
                            recursive, classes);
                }
            }
            return classes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 以文件的形式来获取包下的所有Class
     * @param packageName 包名
     * @param packagePath 包的物理路径
     * @param recursive 是否递归扫描
     * @param classes 类集合
     */
    private void findAndAddClassesInPackageByFile(String packageName,
                                                        String packagePath,
                                                        final boolean recursive, Set<Class<?>> classes) {
        // 获取此包的目录 建立一个File
        File dir = new File(packagePath);
        // 如果不存在或者 也不是目录就直接返回
        if (!dir.exists() || !dir.isDirectory()) {
            System.out.println("用户定义包名 " + packageName + " 下没有任何文件");
            return;
        }
        // 如果存在 就获取包下的所有文件 包括目录
        File[] dirFiles = dir.listFiles(new FileFilter() {
            // 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
            public boolean accept(File file) {
                return (recursive && file.isDirectory())
                        || (file.getName().endsWith(".class"));
            }
        });
        // 循环所有文件
        if (null == dirFiles) {
            return;
        }
        for (File file : dirFiles) {
            // 如果是目录 则递归继续扫描
            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile(packageName + "."
                                + file.getName(), file.getAbsolutePath(), recursive,
                        classes);
            } else {
                // 如果是java类文件 去掉后面的.class 只留下类名
                String className = file.getName().substring(0,
                        file.getName().length() - 6);
                try {
                    // 添加到集合中去
                    classes.add(classLoader.loadClass(packageName + '.' + className));
                } catch (ClassNotFoundException e) {
                    System.out.println("添加用户自定义视图类错误 找不到此类的.class文件");
                    e.printStackTrace();
                }
            }
        }
    }
}
