## Add project specific ProGuard rules here.
## You can control the set of applied configuration files using the
## proguardFiles setting in build.gradle.
##
## For more details, see
##   http://developer.android.com/guide/developing/tools/proguard.html
#
## If your project uses WebView with JS, uncomment the following
## and specify the fully qualified class name to the JavaScript interface
## class:
##-keepclassmembers class fqcn.of.javascript.interface.for.webview {
##   public *;
##}
#
## Uncomment this to preserve the line number information for
## debugging stack traces.
##-keepattributes SourceFile,LineNumberTable
#
## If you keep the line number information, uncomment this to
## hide the original source file name.
##-renamesourcefileattribute SourceFile
#-keep public class com.alibaba.android.arouter.routes.**{*;}
#-keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}
#-dontwarn com.tencent.bugly.**
#-keep public class com.tencent.bugly.**{*;}
#-ignorewarnings
#
##指定压缩级别
#-optimizationpasses 5
#
##不跳过非公共的库的类成员
#-dontskipnonpubliclibraryclassmembers
#
##混淆时采用的算法
#-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
#
##把混淆类中的方法名也混淆了
#-useuniqueclassmembernames
#
##优化时允许访问并修改有修饰符的类和类的成员
#-allowaccessmodification
#
##将文件来源重命名为“SourceFile”字符串
#-renamesourcefileattribute SourceFile
##保留行号
#-keepattributes SourceFile,LineNumberTable
#
##保持所有实现 Serializable 接口的类成员
#-keepclassmembers class * implements java.io.Serializable {
#    static final long serialVersionUID;
#    private static final java.io.ObjectStreamField[] serialPersistentFields;
#    private void writeObject(java.io.ObjectOutputStream);
#    private void readObject(java.io.ObjectInputStream);
#    java.lang.Object writeReplace();
#    java.lang.Object readResolve();
#}
#
##Fragment不需要在AndroidManifest.xml中注册，需要额外保护下
#-keep public class * extends android.support.v4.app.Fragment
#-keep public class * extends android.app.Fragment
#
#
#
##保持 native 方法不被混淆
#-keepclasseswithmembernames class * {
#    native <methods>;
#}
#
##保持自定义控件类不被混淆
#-keepclasseswithmembers class * {
#    public <init>(android.content.Context, android.util.AttributeSet);
#}
#
##保持自定义控件类不被混淆
#-keepclassmembers class * extends android.app.Activity {
#   public void *(android.view.View);
#}
#
#-keep public class * extends android.view.View {
#    public <init>(android.content.Context);
#    public <init>(android.content.Context, android.util.AttributeSet);
#    public <init>(android.content.Context, android.util.AttributeSet, int);
#    public void set*(...);
#}
#
#-keepclassmembers class * extends android.app.Activity { # 保持自定义控件类不被混淆
#    public void *(android.view.View);
#}
#
##保持 Parcelable 不被混淆
#-keep class * implements android.os.Parcelable {
#  public static final android.os.Parcelable$Creator *;
#}
#
#-keep public class * extends android.support.v4.**
#-keep public class * extends android.support.v7.**
#-keep public class * extends android.support.annotation.**
#
##保持 Serializable 不被混淆
#-keepnames class * implements java.io.Serializable
#
##保持 Serializable 不被混淆并且enum 类也不被混淆
#-keepclassmembers class * implements java.io.Serializable {
#    static final long serialVersionUID;
#    private static final java.io.ObjectStreamField[] serialPersistentFields;
#    !static !transient <fields>;
#    !private <fields>;
#    !private <methods>;
#    private void writeObject(java.io.ObjectOutputStream);
#    private void readObject(java.io.ObjectInputStream);
#    java.lang.Object writeReplace();
#    java.lang.Object readResolve();
#}
#
##保持枚举 enum 类不被混淆
#-keepclassmembers enum * {
#  public static **[] values();
#  public static ** valueOf(java.lang.String);
#}
#
#-keepclassmembers class * {
#    public void *ButtonClicked(android.view.View);
#}
#
##不混淆资源类
#-keepclassmembers class **.R$* {
#    public static <fields>;
#}
#
##避免混淆泛型 如果混淆报错建议关掉
#-keepattributes Signature
#
#-keepattributes Exceptions,InnerClasses,...
#-keep class android.support.design.widget.TabLayout{
#    *;
#}
#-keep class android.support.design.widget.TabLayout$* {
#    *;
#}
#
#-dontwarn com.tencent.bugly.**
#-keep public class com.tencent.bugly.**{*;}
#
#
## 保持测试相关的代码
#-dontnote junit.framework.**
#-dontnote junit.runner.**
#-dontwarn android.test.**
#-dontwarn android.support.test.**
#-dontwarn org.junit.**
#
#
##保留阿里巴巴所有的
#-dontwarn com.alibaba.fastjson.**
#-dontwarn com.zhihu.matisse.**
#-dontwarn me.iwf.**
#-dontwarn org.greenrobot.**
#-dontwarn retrofit.**
#-dontwarn rx.internal.**
#
#-keepclassmembers enum * {
#    public static **[] values();
#    public static ** valueOf(java.lang.String);
#}
#
#-keepnames class * implements android.os.Parcelable {
#    public static final ** CREATOR;
#}
#
#-keep class com.linkedin.** { *; }
#-keep class com.android.dingtalk.share.ddsharemodule.** { *; }
#-keepattributes Signature
#
#
## Retain generic type information for use by reflection by converters and adapters.
## retrofit
#-keepattributes Signature
## Retain service method parameters.
#-keepclassmembernames,allowobfuscation interface * {
#    @retrofit2.http.* <methods>;
#}
## Ignore annotation used for build tooling.
#-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
#
## okhttp
#-dontwarn okhttp3.**
#-dontwarn okio.**
#-dontwarn javax.annotation.**
## A resource is loaded with a relative path so the package of this class must be preserved.
#-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase
#
## BaseRecyclerViewAdapterHelper
#-keep class com.chad.library.adapter.** {
#*;
#}
#-keep public class * extends com.chad.library.adapter.base.BaseQuickAdapter
#-keep public class * extends com.chad.library.adapter.base.BaseViewHolder
#-keepclassmembers  class **$** extends com.chad.library.adapter.base.BaseViewHolder {
#     <init>(...);
#}
#
#
## EventBus
#-keepattributes *Annotation*
#-keepclassmembers class ** {
#    @org.greenrobot.eventbus.Subscribe <methods>;
#}
#-keep enum org.greenrobot.eventbus.ThreadMode { *; }
#
## Only required if you use AsyncExecutor
#-keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
#    <init>(java.lang.Throwable);
#}
#
## UCrop
#-dontwarn com.yalantis.ucrop**
#-keep class com.yalantis.ucrop** { *; }
#-keep interface com.yalantis.ucrop** { *; }
#
#-dontwarn com.squareup.okhttp.**
#
## Glide
#-keep public class * implements com.bumptech.glide.module.GlideModule
#-keep public class * extends com.bumptech.glide.module.AppGlideModule
#-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
#  **[] $VALUES;
#  public *;
#}
#
## If you do not use Rx:
#-dontwarn rx.**
#
#
#-dontwarn javax.annotation.**
#-dontwarn javax.inject.**
## OkHttp3
#-dontwarn okhttp3.logging.**
#-keep class okhttp3.internal.**{*;}
#-dontwarn okio.**
## Retrofit
#-dontwarn retrofit2.**
#-keep class retrofit2.** { *; }
##-keepattributes Signature-keepattributes Exceptions
## RxJava RxAndroid
#-dontwarn sun.misc.**
#-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
#    long producerIndex;
#    long consumerIndex;
#}
#-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
#    rx.internal.util.atomic.LinkedQueueNode producerNode;
#}
#-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
#    rx.internal.util.atomic.LinkedQueueNode consumerNode;
#}
#
## Gson
#-keep class com.google.gson.stream.** { *; }
#-keepattributes EnclosingMethod
#
## ARouter
#-keep public class com.alibaba.android.arouter.routes.**{*;}
#-keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}
#-keep interface * implements com.alibaba.android.arouter.facade.template.IProvider
#
#-keep public class * extends com.cjwsc.idcm.net.response.HttpResponse {
#    *;
#}
#-keep class retroficom.android.okhttp2.** { *; }
#
## base
#-keep class com.idcg.idcw.model.** {
#    *;
#}
## pay
#-keep class com.idcw.pay.model.** {
#    *;
#}
## app
#-keep class com.idcg.idcw.model.** {
#    *;
#}
## otc
#-keep class foxidcw.android.idcw.otc.model.** {
#    *;
#}
##common
#-keep class foxidcw.android.idcw.common.model.** {
#    *;
#}
##FoxArouter
#-keep class foxidcw.android.idcw.foxcommon.provider.** {
#    *;
#}
##Base
#-keep class com.cjws.idcm.model.** {
#    *;
#}
#
## 推送的
#-keep class doc.**{*;}
#-keep class lib.**{*;}
#-keep class META-INF.**{*;}
#-keep class microsoft.**{*;}
#-keep class resources.**{*;}
#
##
#-keep public class * extends com.chad.library.adapter.base.BaseViewHolder {
#    *;
#}
#
## simple eventbus
#-keep class org.simple.** { *; }
#-keep interface org.simple.** { *; }
#-keepclassmembers class * {
#    @org.simple.eventbus.Subscriber <methods>;
#}
#-keepattributes *Annotation*
#
#-keep class com.cjwsc.idcm.model.bean.** { *; }
#
#
#
#-keep class cn.sharesdk.**{*;}
#-keep class com.sina.**{*;}
#-keep class **.R$* {*;}
#-keep class **.R{*;}
#-keep class com.mob.**{*;}
#-keep class m.framework.**{*;}
#-dontwarn cn.sharesdk.**
#-dontwarn com.sina.**
#-dontwarn com.mob.**
#-dontwarn **.R$*
#
## mobshare
#-keep class cn.sharesdk.**{*;}
#-keep class com.sina.**{*;}
#-keep class **.R$* {*;}
#-keep class **.R{*;}
#-keep class com.mob.**{*;}
#-keep class m.framework.**{*;}
#-dontwarn cn.sharesdk.**
#-dontwarn com.sina.**
#-dontwarn com.mob.**
#-dontwarn **.R$*
#
#
#-keep class org.simple.** { *; }
#-keep interface org.simple.** { *; }
#-keepclassmembers class * {
#    @org.simple.eventbus.Subscriber <methods>;
#}
#-keepattributes *Annotation*
#
#-keep class org.litepal.** {
#    *;
#}
#
#-keep class * extends org.litepal.crud.DataSupport {
#    *;
#}
#
#-keep class * extends org.litepal.crud.LitePalSupport {
#    *;
#}
#
#
