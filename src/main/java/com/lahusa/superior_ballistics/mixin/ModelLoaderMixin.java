package com.lahusa.superior_ballistics.mixin;

//@Mixin(ModelLoader.class)
//public class ModelLoaderMixin {
//    @Inject(method = "loadModelFromJson", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/ResourceManager;getResource(Lnet/minecraft/util/Identifier;)Lnet/minecraft/resource/Resource;"))
//    public void loadModelFromJson(Identifier id, CallbackInfoReturnable<JsonUnbakedModel> cir) {
//        if(id.getNamespace().equals(SuperiorBallisticsMod.MODID)) {
//            System.out.println("loadModelFromJson: " + id.toString());
//        }
//    }
//
//    @Redirect(method = "loadModel", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/ResourceManager;getAllResources(Lnet/minecraft/util/Identifier;)Ljava/util/List;"))
//    public List<Resource> loadModel(ResourceManager self, Identifier id) {
//        if(id.getNamespace().equals(SuperiorBallisticsMod.MODID)) {
//            System.out.println("loadModel: " + id.toString());
//        } else {
//            try {
//                return self.getAllResources(id);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        return null;
//    }
//}