public class Main {
    public static void main(String[] args) {
        int numGamesPerYear = 15;
        int gameCoast = 10000000; // in USD
        int numEmployees = 1200;
        int hoursPerEmployeePerGame = 8;
        int numEmployeesPerGame = 15;

        int totalGames = numGamesPerYear;
        int totalCost = gameCoast * numGamesPerYear;
        int totalEmployeeHoursPerYear = hoursPerEmployeePerGame * numEmployeesPerGame * numGamesPerYear;
        int totalEmployeeCostPerYear = totalEmployeeHoursPerYear * numEmployees;

        System.out.println("Total games created per year: " + totalGames);
        System.out.println("Total cost of creating games per year: $" + totalCost);
        System.out.println("Total employee hours per year: " + totalEmployeeHoursPerYear + " hours");
        System.out.println("Total employee cost per year: $" + totalEmployeeCostPerYear);
    }
}
