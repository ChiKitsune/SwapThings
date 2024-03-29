package chikitsune.swap_things.commands.arguments;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import com.mojang.brigadier.Message;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.*;

public class RandomEffectType implements ArgumentType<String> {
 public static Set<String> reTypeIDs = new HashSet<>(Arrays.asList("ANY","POSITIVE","NEGATIVE","RANDOM"));
 private static final Collection<String> EXAMPLES = Arrays.asList("ANY","POSITIVE","NEGATIVE","RANDOM");
 private static final DynamicCommandExceptionType NOT_IN_LIST =
   new DynamicCommandExceptionType(msg -> Component.translatable("arguments.randomeffecttype.notFound"));
   //new DynamicCommandExceptionType(Component.translatable("arguments.randomeffecttype.notFound"));
 //private static final DynamicCommandExceptionType NOT_IN_LIST = new DynamicCommandExceptionType(msg -> new TranslatableComponent("arguments.randomeffecttype.notFound", msg));
 
 public static RandomEffectType randomEffectType() { return new RandomEffectType();}
 
 public static String getRandomEffectType(CommandContext<CommandSourceStack> context, String name) {
  return context.getArgument(name, String.class);
}
 
 @Override
 public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) { return SharedSuggestionProvider.suggest(reTypeIDs, builder); }

 @Override
 public String parse(StringReader reader) throws CommandSyntaxException {
  String s = reader.readUnquotedString();
  if (!reTypeIDs.contains(s)) {
    throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.literalIncorrect().createWithContext(reader,Component.translatable("arguments.randomeffecttype.notFound"));
  } else {
    return s;
  }
 }
 
 @Override
 public Collection<String> getExamples() { return EXAMPLES; }

}
