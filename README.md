# My Maven Library
[![Build Status](https://travis-ci.org/mickey305/maven.svg?branch=gh-pages)](https://travis-ci.org/mickey305/maven)  

This is the Maven Project, built by IntelliJ IDEA etc.

When using a library of this repository, please add this link: [http://mickey305.github.io/maven/][home] to a `build.gradle` file. For more information, I will describe in the next section.

# Installation
Gradle projects are managed by `build.gradle` files in their root directory. Please insert `build.gradle` file in their root directry or sub-project directory if you might use this library to build your gradle project.

### Register repository in local library

```
repositories {
    maven { url 'http://mickey305.github.io/maven/repository/' }

}
```

### Compile library

e.g.
```
dependencies {
    compile 'com.mickey305:common-library:0.0.1'

}
```

### Gradle's useful links
* [Gradle's User Guide][gradle-user-guide]
* [Gradle's Dependency Management Guide][gradle-dependency-management-guide]

# History

### android-common-library
##### 0.0.1
* java version - jdk7 on the dalvik.
* android API higher than 19 (Kitkat).
* same v0.0.3 of the common-library.
* gradle code: `compile 'com.mickey305:android-common-library:0.0.1'`

### common-library
##### 0.0.2 - 0.0.3
* java version - jdk8.
* Bugfix - RingBuffer class.
* JSON analysis libs added.
* gradle code: `compile 'com.mickey305:common-library:0.0.2'`
* [detail][common-0.0.2]

##### 0.0.1
* java version  - higher than jdk7.
* First release.
* RingBuffer class added.
* gradle code: `compile 'com.mickey305:common-library:0.0.1'`
* [detail][common-0.0.1]



[home]: http://mickey305.github.io/maven/
[gradle-user-guide]: http://www.gradle.org/docs/current/userguide/userguide.html
[gradle-dependency-management-guide]: http://www.gradle.org/docs/current/userguide/dependency_management.html

[common-0.0.1]: projects/common-library/detail-markdown/0.0.1.md
[common-0.0.2]: projects/common-library/detail-markdown/0.0.2.md
