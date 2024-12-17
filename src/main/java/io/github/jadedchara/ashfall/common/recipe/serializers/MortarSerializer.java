package io.github.jadedchara.ashfall.common.recipe.serializers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import io.github.jadedchara.ashfall.common.Ashfall;
import io.github.jadedchara.ashfall.common.recipe.MortarRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.List;

public class MortarSerializer implements RecipeSerializer<MortarRecipe> {
    @Override
    public MortarRecipe read(Identifier id, JsonObject json) {

        MortarRecipe.MortarRecipeFormat recipeJson = new Gson().fromJson(json, MortarRecipe.MortarRecipeFormat.class);
        if (recipeJson.input == null || recipeJson.output == null) {
            throw new JsonSyntaxException("A required attribute is missing!");
        }
        Ingredient input = Ingredient.fromJson(recipeJson.input);
        Ingredient mixComponentA; //= Ingredient.fromJson(recipeJson.mixComponentA,true);
        Ingredient mixComponentB; //= Ingredient.fromJson(recipeJson.mixComponentB,true);
        try{
            mixComponentA = Ingredient.fromJson(recipeJson.mixComponentA,true);
        }catch(Exception e){
            System.out.println(e);
            mixComponentA = Ingredient.ofItems(Items.AIR);
        }
        try{
            mixComponentB = Ingredient.fromJson(recipeJson.mixComponentB,true);
        }catch(Exception e){
            System.out.println(e);
            mixComponentB = Ingredient.ofItems(Items.AIR);
        }

        Item outputItem =
                Registries.ITEM.getOrEmpty(new Identifier(recipeJson.output))
                        .orElseThrow(() -> new JsonSyntaxException("No such item: " + recipeJson.output));
        ItemStack output = new ItemStack(outputItem);
        int experience = recipeJson.experience;
        int grindtime = recipeJson.grindtime;
        List<Ingredient> items = List.of(input,mixComponentA,mixComponentB);

        return new MortarRecipe(id,items,output,experience,grindtime);
    }
    @Override
    public void write(PacketByteBuf packetData, MortarRecipe recipe) {
        recipe.getInput().write(packetData);
        packetData.writeItemStack(recipe.getResult());
        packetData.writeInt(recipe.getExperience());
        packetData.writeInt(recipe.getGrindtime());
        recipe.getMixComponentA().write(packetData);
        recipe.getMixComponentB().write(packetData);
    }

    @Override
    public MortarRecipe read(Identifier id, PacketByteBuf packetData) {
        Ingredient input = Ingredient.fromPacket(packetData);
        Ingredient mixComponentA = Ingredient.fromPacket(packetData);
        Ingredient mixComponentB = Ingredient.fromPacket(packetData);
        ItemStack output = packetData.readItemStack();
        int experience = packetData.readInt();
        int grindtime = packetData.readInt();

        List<Ingredient> items = List.of(input,mixComponentA,mixComponentB);
        return new MortarRecipe(id,items, output,experience,grindtime);
    }

    private MortarSerializer() {
    }

    public static final MortarSerializer INSTANCE = new MortarSerializer();

    // This will be the "type" field in the json
    public static final Identifier ID = new Identifier(Ashfall.MOD_ID,"mortar_n_pestle");
}
