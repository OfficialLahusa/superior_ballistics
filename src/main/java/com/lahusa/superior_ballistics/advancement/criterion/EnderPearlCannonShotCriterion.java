package com.lahusa.superior_ballistics.advancement.criterion;

import com.google.gson.JsonObject;
import com.lahusa.superior_ballistics.SuperiorBallisticsMod;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class EnderPearlCannonShotCriterion extends AbstractCriterion<EnderPearlCannonShotCriterion.Conditions> {
    static final Identifier ID = new Identifier(SuperiorBallisticsMod.MODID, "ender_pearl_cannon_shot");

    @Override
    public Identifier getId() {
        return ID;
    }

    public void trigger(ServerPlayerEntity player) {
        this.trigger(player, conditions -> conditions.matches(player));
    }

    @Override
    protected Conditions conditionsFromJson(JsonObject obj, LootContextPredicate playerPredicate, AdvancementEntityPredicateDeserializer predicateDeserializer) {
        return new EnderPearlCannonShotCriterion.Conditions(playerPredicate);
    }

    public static class Conditions extends AbstractCriterionConditions {

        public Conditions(LootContextPredicate playerPredicate) {
            super(ID, playerPredicate);
        }

        @Override
        public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
            return super.toJson(predicateSerializer);
        }

        public boolean matches(ServerPlayerEntity player) {
            return true;
        }
    }
}
