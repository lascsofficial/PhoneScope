# PhoneScope ProGuard Rules

# Keep JNI methods (never obfuscate native bridge)
-keepclasseswithmembernames class com.phonescope.inspector.data.jni.** {
    native <methods>;
}

# Keep kotlinx.serialization classes
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt

-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}

-keepclasseswithmembers class kotlinx.serialization.json.** {
    kotlinx.serialization.KSerializer serializer(...);
}

-keep,includedescriptorclasses class com.phonescope.inspector.**$$serializer { *; }
-keepclassmembers class com.phonescope.inspector.** {
    *** Companion;
}
-keepclasseswithmembers class com.phonescope.inspector.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# Keep Room entities
-keep class com.phonescope.inspector.data.local.entity.** { *; }

# Keep Hilt generated code
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }
-keep class * extends dagger.hilt.android.internal.lifecycle.HiltViewModelFactory { *; }
