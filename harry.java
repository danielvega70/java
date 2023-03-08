import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // Define the symbols
        Symbol rain = new Symbol("rain"); // It is raining
        Symbol hagrid = new Symbol("hagrid"); // Harry visited Hagrid
        Symbol dumbledore = new Symbol("dumbledore"); // Harry visited Dumbledore
        Symbol mustard = new Symbol("mustard"); // Mr. Mustard is the killer
        Symbol plum = new Symbol("plum"); // Professor Plum is the killer
        Symbol scarlet = new Symbol("scarlet"); // Miss Scarlet is the killer
        Symbol ballroom = new Symbol("ballroom"); // The murder happened in the ballroom
        Symbol kitchen = new Symbol("kitchen"); // The murder happened in the kitchen
        Symbol library = new Symbol("library"); // The murder happened in the library
        Symbol knife = new Symbol("knife"); // The murder weapon is a knife
        Symbol revolver = new Symbol("revolver"); // The murder weapon is a revolver
        Symbol wrench = new Symbol("wrench"); // The murder weapon is a wrench

        // Define the knowledge base
        Sentence[] sentences = {
                new Implication(new Not(rain), hagrid), // If it's not raining, Harry visited Hagrid
                new Or(mustard, plum, scarlet), // The killer is one of Mr. Mustard, Professor Plum, or Miss Scarlet
                new Or(ballroom, kitchen, library), // The murder happened in one of the ballroom, kitchen, or
