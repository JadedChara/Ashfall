package io.github.jadedchara.ashfall.common.recipe;

import com.google.gson.JsonObject;
import io.github.jadedchara.ashfall.api.oddity.ImplInventory;
import io.github.jadedchara.ashfall.common.recipe.serializers.MortarSerializer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MortarRecipe implements Recipe<CraftingInventory> {
    public Identifier id;
    public Ingredient input;
    public Ingredient mixComponentA;
    public Ingredient mixComponentB;
    public ItemStack output;
    public int experience;
    public int grindtime;
    public List<Ingredient> recipeItems;



    public MortarRecipe(Identifier i, List<Ingredient> ingredients, ItemStack out,
                        int e, int gt){
        super();
        this.id = i;

        this.recipeItems = ingredients;
        this.input = ingredients.get(0);
        this.output = out;
        this.mixComponentA = ingredients.get(1);
        this.mixComponentB = ingredients.get(2);

        this.experience = e;
        this.grindtime = gt;
        System.out.println(
                "Mortar N' Pestle recipe registered:\n--Primary Ingredient: "
                        + this.input
                        +"\n--Mix Part A:"
                        +this.mixComponentA
                        +"\n--Mix Part B:"
                        +this.mixComponentB
                        +"\n--Output:"
                        +this.output
                        +"\n-----------"
        );
    }

    public Ingredient getInput() {
        return this.input;
    }

    public ItemStack getResult() {
        return this.output;
    }
    public Ingredient getMixComponentA() {
        return this.mixComponentA;
    }
    public Ingredient getMixComponentB() {
        return this.mixComponentB;
    }
    public int getExperience(){
        return this.experience;
    }
    public int getGrindtime(){
        return this.grindtime;
    }

    @Override
    public Identifier getId() {
        return this.id;
    }
    @Override
    public boolean matches(CraftingInventory inv, World world) {
        if(!world.isClient()/* || inv.size() < 3*/) {
            //return false;
        //}

        return (recipeItems.get(0).test(inv.getStack(0))&&
                recipeItems.get(1).test(inv.getStack(1))&&
                recipeItems.get(2).test(inv.getStack(2))

        );
        }
        return false;
    }
    @Override
    public ItemStack craft(CraftingInventory inventory, DynamicRegistryManager rm) {
        //System.out.println("Match found!");
        //return this.output.copy();
        return this.getOutput(rm).copy();
        //ShapedRecipe
    }
    @Override
    public boolean fits(int width, int height) {
        return true;
    }
    @Override
    public ItemStack getOutput(DynamicRegistryManager registryManager) {
        return output;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> list = DefaultedList.ofSize(this.recipeItems.size());
        list.addAll(recipeItems);
        return list;
    }


    public static class Type implements RecipeType<MortarRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "mortar_recipe";
    }


    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }


    @Override
    public boolean isEmpty() {
        return Recipe.super.isEmpty();
    }

    public static class MortarRecipeFormat{
        public JsonObject input;
        public JsonObject mixComponentA;
        public JsonObject mixComponentB;
        public String output;
        public int experience;
        public int grindtime;
    }
    @Override
    public RecipeSerializer<?> getSerializer() {
        return MortarSerializer.INSTANCE;
    }

}
