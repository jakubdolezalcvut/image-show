buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(embeddedKotlin("gradle-plugin"))
        classpath("com.android.tools.build:gradle:3.4.2")
        classpath("de.mannodermaus.gradle.plugins:android-junit5:1.5.0.0")
    }
}
allprojects {
    repositories {
        google()
        jcenter()
    }
}