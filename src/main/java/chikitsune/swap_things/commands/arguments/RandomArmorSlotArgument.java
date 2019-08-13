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

import net.minecraft.command.CommandSource;
import net.minecraft.command.ISuggestionProvider;
import net.minecraft.util.text.TranslationTextComponent;

public class RandomArmorSlotArgument implements ArgumentType<String> {
 private static final Collection<String> EXAMPLES = Arrays.asList("MAINHAND", "OFFHAND","FEET","LEGS","CHEST","HEAD","RANDOM","SET","ALL"); 
 private static final DynamicCommandExceptionType NOT_IN_LIST = new DynamicCommandExceptionType((msg) -> { return new TranslationTextComponent("arguments.randomarmorslot.notFound", msg); });
 
 public static RandomArmorSlotArgument allArmorSlots() { return new RandomArmorSlotArgument(); }
 
 public static String getRandomArmorSlot(CommandContext<CommandSource> context, String name) throws CommandSyntaxException {
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
  return ISuggestionProvider.suggest(EXAMPLES, suggestList);
}

 public Collection<String> getExamples() { return EXAMPLES; }
}