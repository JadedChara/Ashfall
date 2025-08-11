package io.github.jadedchara.ashfall.common.recipe.serializers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import io.github.jadedchara.ashfall.common.Ashfall;
import io.github.jadedchara.ashfall.common.recipe.MortarRecipe;
import net.minecraft.block.AirBlock;
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
        if (recipeJson.input == null || recipeJson.output == null || recipeJson.input instanceof AirBlock) {
            throw new JsonSyntaxException("A required attribute is missing!");
        }
        Ingredient inputA = Ingredient.fromJson(recipeJson.input.get(0));

        Ingredient inputB; //= Ingredient.fromJson(recipeJson.mixComponentA,true);
        Ingredient inputC; //= Ingredient.fromJson(recipeJson.mixComponentB,true);
        try{
            inputB = Ingredient.fromJson(recipeJson.input.get(1),true);
        }catch(Exception e){
            //System.out.println(e);
            inputB = Ingredient.ofItems(Items.AIR);
        }
        try{
            inputC = Ingredient.fromJson(recipeJson.input.get(2),true);
        }catch(Exception e){
            //System.out.println(e);
            inputC = Ingredient.ofItems(Items.AIR);
        }

        Item outputItem =
                Registries.ITEM.getOrEmpty(new Identifier(recipeJson.output))
                        .orElseThrow(() -> new JsonSyntaxException("No such item: " + recipeJson.output));
        ItemStack output = new ItemStack(outputItem);
        int experience = recipeJson.experience;
        int grindtime = recipeJson.grindtime;
        int amount = recipeJson.amount;
        List<Ingredient> items = List.of(inputA,inputB,inputC);

        return new MortarRecipe(id,items,output,amount,experience,grindtime);
    }
    @Override
    public void write(PacketByteBuf packetData, MortarRecipe recipe) {
        recipe.getInput().get(0).write(packetData);
        recipe.getInput().get(1).write(packetData);
        recipe.getInput().get(2).write(packetData);
        packetData.writeItemStack(recipe.getResult());
        packetData.writeInt(recipe.getResult().getCount());
        packetData.writeInt(recipe.getExperience());
        packetData.writeInt(recipe.getGrindtime());

    }

    @Override
    public MortarRecipe read(Identifier id, PacketByteBuf packetData) {
        Ingredient inputA =  Ingredient.fromPacket(packetData);
        Ingredient inputB = Ingredient.fromPacket(packetData);
        Ingredient inputC = Ingredient.fromPacket(packetData);
        ItemStack output = packetData.readItemStack();
        int amount = packetData.readInt();
        int experience = packetData.readInt();
        int grindtime = packetData.readInt();

        List<Ingredient> items = List.of(inputA,inputB,inputC);
        return new MortarRecipe(id,items,output,amount,experience,grindtime);
    }

    private MortarSerializer() {
    }

    public static final MortarSerializer INSTANCE = new MortarSerializer();
    public static final Identifier ID = new Identifier("ashfall:mortar_n_pestle");

}
