/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class android_com_bitchat_controler_GsSocketManager */
#ifndef _Included_android_com_bitchat_controler_GsSocketManager
#define _Included_android_com_bitchat_controler_GsSocketManager
#ifdef __cplusplus
extern "C" {
#endif
//---------------------------------------- APIs --------------------------------------------
/*
 * Class:     android_com_bitchat_controler_GsSocketManager
 * Method:    helloGoonas
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_android_com_bitchat_controler_GsSocketManager_helloBitchat
  (JNIEnv *, jobject);


/*
 * Class:     android_com_bitchat_controler_gchatgenkey
 * Method:    gchatgenkey
 * Signature: ()Z
 */

JNIEXPORT jboolean JNICALL Java_android_com_bitchat_controler_GsSocketManager_gchatgenkey
  (JNIEnv *, jobject, jstring);

/*
 * Class:     android_com_bitchat_controler_GsSocketManager_gchat_login
 * Method:    gchat_login
 * Signature: (ILjava/lang/String;Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_android_com_bitchat_controler_GsSocketManager_gchatlogin
        (JNIEnv *, jobject, jstring, jint, jstring, jstring, jstring);


/*
 * Class:     android_com_bitchat_controler_GsSocketManager_gchat_logout
 * Method:    gchat_logout
 * Signature: (ILjava/lang/String;Ljava/lang/String;)I
 */
 JNIEXPORT jint JNICALL Java_android_com_bitchat_controler_GsSocketManager_gchatlogout
        (JNIEnv *, jobject);


/*
 * Class:     android_com_bitchat_controler_GsSocketManager_gchat_search
 * Method:    gchat_search
 * Signature: (ILjava/lang/String;Ljava/lang/String;)I
 */
JNIEXPORT jstring JNICALL Java_android_com_bitchat_controler_GsSocketManager_gchatsearch
        (JNIEnv *, jobject, jstring);


/*
 * Class:     android_com_bitchat_controler_GsSocketManager_gchat_connect_friend
 * Method:    gchat_connect_friend
 * Signature: (ILjava/lang/String;Ljava/lang/String;)I
 */
JNIEXPORT jstring JNICALL Java_android_com_bitchat_controler_GsSocketManager_gchatconnectfriend
        (JNIEnv *, jobject, jstring);


/*
 * Class:     android_com_bitchat_controler_GsSocketManager_gchat_chat
 * Method:    gchat_chat
 * Signature: (ILjava/lang/String;Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_android_com_bitchat_controler_GsSocketManager_gchatchat
        (JNIEnv *, jobject, jstring, jstring);


/*
 * Class:     android_com_bitchat_controler_GsSocketManager_gchat_quit_chat
 * Method:    gchat_quit_chat
 * Signature: (ILjava/lang/String;Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_android_com_bitchat_controler_GsSocketManager_gchatquitchat
        (JNIEnv *, jobject);


/*
 * Class:     android_com_bitchat_controler_gsGproxyInit
 * Method:    gsGproxyInit
 * Signature: ()Z
 */

JNIEXPORT jboolean JNICALL Java_android_com_bitchat_controler_GsSocketManager_gsGproxyInit
  (JNIEnv *, jobject, jstring, jint, jstring, jstring);

/*
 * Class:     android_com_bitchat_controler_GsSocketManager_gsPutFileIdRetId2
 * Method:    gsPutFileIdRetId2
 * Signature: (ILjava/lang/String;Ljava/lang/String;)I
 */
JNIEXPORT jstring JNICALL Java_android_com_bitchat_controler_GsSocketManager_gsPutFileIdRetId2
        (JNIEnv *, jobject,jint, jstring, jstring, jstring);

/*
 * Class:     android_com_bitchat_controler_GsSocketManager_gsPutFileId2
 * Method:    gsPutFileId2
 * Signature: (ILjava/lang/String;Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Java_android_com_bitchat_controler_GsSocketManager_gsPutFileId2
        (JNIEnv *, jobject,jint, jstring, jstring, jstring);

#ifdef __cplusplus
}
#endif
#endif