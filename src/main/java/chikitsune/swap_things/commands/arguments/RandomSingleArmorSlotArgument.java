package chikitsune.swap_things.commands.arguments;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;

public class RandomSingleArmorSlotArgument implements ArgumentType<String> {
 private static final Collection<String> EXAMPLES = Arrays.asList("MAINHAND", "OFFHAND","FEET","LEGS","CHEST","HEAD","RANDOM");
 private static final DynamicCommandExceptionType NOT_IN_LIST = new DynamicCommandExceptionType((msg) -> { return Component.translatable("arguments.randomsinglearmorslot.notFound", msg); });
 
 public static RandomSingleArmorSlotArgument allArmorSlots() { return new RandomSingleArmorSlotArgument(); }
 
 public static String getSingleRandomArmorSlot(CommandContext<CommandSourceStack> context, String name) throws CommandSyntaxException {
  return context.getArgument(name, String.class);
}
  
 @Override
 public String parse(StringReader reader) throws CommandSyntaxException {
  String tempStr=reader.readUnquotedString();
  
  if (EXAMPLES.contains(tempStr.toUpperCase())) {
   return tempStr;
  } else {
   throw NOT_IN_LIST.create(tempStr);
  }
 }
 
 public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder suggestList) {
  return SharedSuggestionProvider.suggest(EXAMPLES, suggestList);
}

 public Collection<String> getExamples() { return EXAMPLES; }
}