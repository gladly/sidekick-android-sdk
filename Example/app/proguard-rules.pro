# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

####################################################################

#-keepattributes *Annotation*, Signature, Exception, JavascriptInterface, InnerClasses
#-keepclassmembers class com.gladly.androidchatsdk.internal.** { *; }
#-keepclasseswithmembernames class com.gladly.androidchatsdk.internal.storage.** { *; }
#
## Don't obfuscate/minify away reflection stuff...
#-keep class kotlin.Metadata { *; }
#-keepclassmembers public class com.gladly.androidchatsdk.** {
#    public synthetic <methods>;
#}
#-keepclassmembers class kotlin.Metadata {
#    public <methods>;
#}
#
## Need to keep these for Snowplow Tracker
#-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient {*;}
#-keep class com.google.android.gms.ads.identifier.AdvertisingIdClient$Info {*;}
#
#-dontwarn com.snowplowanalytics.**
#-keep class com.snowplowanalytics.** { *; }

####################################################################