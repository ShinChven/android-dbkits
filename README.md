# android-dbkits
this library contains 2 utils.
- ContentValueWriter helps you to reflect your entity to ContentValues.
- CursorReader helps you to reflect cursor data to your entity.

# tips
- field names of your entity should be the same with column names.

# usage
you can use this library through <a href="https://jitpack.io">JitPack.io</a>

``` gradle
   allprojects {
        repositories {
            jcenter()
            maven { url "https://jitpack.io" }
        }
   }

   dependencies {
        compile 'com.github.ShinChven:android-dbkits:1.0.0'
   }
```
