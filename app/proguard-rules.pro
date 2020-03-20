#---start Glide--------------
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
#---end Glide------------------

#Cropper---------------
-keep class androidx.appcompat.widget.** { *; }
#End Cropper-----------
