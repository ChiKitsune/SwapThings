package chikitsune.swap_things.commands.arguments;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
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

public class RandomEffectTypeArgument implements ArgumentType<String> {
 public static Set<String> reTypeIDs = new HashSet<>(Arrays.asList("ANY","POSITIVE","NEGATIVE","RANDOM"));
 private static final Collection<String> EXAMPLES = Arrays.asList("ANY","POSITIVE","NEGATIVE","RANDOM");
 private static final DynamicCommandExceptionType NOT_IN_LIST = new DynamicCommandExceptionType(msg -> Component.translatable("arguments.randomeffecttype.notFound", msg));
 
 public static RandomEffectTypeArgument randomEffectTypeArgument() { return new RandomEffectTypeArgument();}
 
 public static String getRandomEffectType(CommandContext<CommandSourceStack> context, String name) {
  return context.getArgument(name, String.class);
}
 
 @Override
 public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) { return SharedSuggestionProvider.suggest(reTypeIDs, builder); }

 @Override
 public String parse(StringReader reader) throws CommandSyntaxException {
  String s = reader.readUnquotedString();
  if (!reTypeIDs.contains(s)) {
    throw NOT_IN_LIST.create(s);
  } else {
    return s;
  }
 }
 
 @Override
 public Collection<String> getExamples() { return EXAMPLES; }

}
