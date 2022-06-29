package com.lahusa.superior_ballistics.mixin;

import com.lahusa.superior_ballistics.CannonResourceGenerator;
import com.lahusa.superior_ballistics.SuperiorBallisticsMod;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.IOException;
import java.util.List;

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