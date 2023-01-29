package ceng.ceng351.foodrecdb;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class FOODRECDB implements IFOODRECDB {

    // information of database
    private static String user = "e2448595"; // TODO: Your userName
    private static String password = "Si0QJCDRZcP6jwEP"; //  TODO: Your password
    private static String host = "momcorp.ceng.metu.edu.tr"; // host name
    private static String database = "db2448595"; // TODO: Your database name
    private static int port = 8080; // port

    // Connection object to connect to the database
    private Connection con;

    @Override
    public void initialize() {
        String url = "jdbc:mysql://" + host + ":" + port + "/" + database;

        try {
            // initialize the connection with the database
            Class.forName("com.mysql.jdbc.Driver");
            this.con = DriverManager.getConnection(url, user, password);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int createTables() {
        int result;
        int numberOfTablesInserted = 0;
        String[] queries = new String[5];

        // log the method call
        System.out.println("--- createTables ---");

        // write your SQL queries to be executed in a string form
        // SQL query to create MenuItems table
        String queryToCreateMenuItemsTable = "CREATE TABLE MenuItems (" +
                "itemID int," +
                "itemName char(40)," +
                "cuisine char(20)," +
                "price int," +
                "primary key (itemID)" +
                ")";
        // SQL query to create Ingredients table
        String queryToCreateIngredientsTable = "CREATE TABLE Ingredients (" +
                "ingredientID int," +
                "ingredientName char(40)," +
                "primary key (ingredientID)" +
                ")";
        // SQL query to create Includes table (relationship set)
        String queryToCreateIncludesTable = "CREATE TABLE Includes (" +
                "itemID int," +
                "ingredientID int," +
                "primary key (itemID, ingredientID)," +
                "foreign key (itemID) references MenuItems (itemID)," +
                "foreign key (ingredientID) references Ingredients (ingredientID)" +
                ")";
        // SQL query to create Ratings table
        String queryToCreateRatingsTable = "CREATE TABLE Ratings (" +
                "ratingID int," +
                "itemID int," +
                "rating int," +
                "ratingDate Date," +
                "primary key (ratingID)," +
                "foreign key (itemID) references MenuItems (itemID)" +
                ")";
        // SQL query to create DietaryCategories table
        String queryToCreateDietaryCategoriesTable = "CREATE TABLE DietaryCategories (" +
                "ingredientID int," +
                "dietaryCategory char(20)," +
                "primary key (ingredientID, dietaryCategory)," +
                "foreign key (ingredientID) references Ingredients (ingredientID)" +
                ")";

        // fill in the queries array
        queries[0] = queryToCreateMenuItemsTable;
        queries[1] = queryToCreateIngredientsTable;
        queries[2] = queryToCreateIncludesTable;
        queries[3] = queryToCreateRatingsTable;
        queries[4] = queryToCreateDietaryCategoriesTable;

        // execute the queries
        for (int i = 0; i < 5; i++) {
            try {
                // Statement: The object (actually class) used for executing a static SQL statement and returning the results it produces
                Statement statement = this.con.createStatement();

                // execute the current query
                // executeUpdate is a method of Statement class that executes the SQL code given as a string parameter
                // returns: either (1) the row count for SQL Data Manipulation Language (DML) statements or (2) 0 for SQL statements that return nothing
                result = statement.executeUpdate(queries[i]);
                // print the result
                System.out.println(result);
                numberOfTablesInserted++;

                // close statement
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return numberOfTablesInserted;
    }

    @Override
    public int dropTables() {
        int result;
        int numberOfTablesDropped = 0;
        String[] queries = new String[5];

        // log the method call
        System.out.println("--- dropTables ---");

        // write your SQL queries to be executed in a string form
        String queryToDropIncludes = "drop table if exists Includes";
        String queryToDropRatings = "drop table if exists Ratings";
        String queryToDropDietaryCategories = "drop table if exists DietaryCategories";
        String queryToDropMenuItems = "drop table if exists MenuItems";
        String queryToDropIngredients = "drop table if exists Ingredients";

        // fill in the queries array
        queries[0] = queryToDropIncludes;
        queries[1] = queryToDropRatings;
        queries[2] = queryToDropDietaryCategories;
        queries[3] = queryToDropMenuItems;
        queries[4] = queryToDropIngredients;

        // execute the queries
        for (int i = 0; i < 5; i++) {
            try {
                Statement statement = this.con.createStatement();

                // execute the current query
                result = statement.executeUpdate(queries[i]);
                numberOfTablesDropped++;
                System.out.println(result);

                //close
                statement.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return numberOfTablesDropped;
    }

    @Override
    public int insertMenuItems(MenuItem[] items) {
        int result;
        int numberOfRowsInserted = 0;
        int lengthOfTheGivenArray = items.length;
        String queryPrefix = "insert into MenuItems values";

        // log the method call
        System.out.println("--- insertMenuItems ---");

        // for all given items
        for (int i = 0; i < lengthOfTheGivenArray; i++) {
            // create the query to be executed
            String query = queryPrefix + "('" +
                    items[i].getItemID()+ "','" +
                    items[i].getItemName() + "','" +
                    items[i].getCuisine() + "','" +
                    items[i].getPrice() + "')";

            // try to execute the query
            try {
                Statement statement = this.con.createStatement();

                // execute the query
                result = statement.executeUpdate(query);
                // print the result
                System.out.println(result);
                numberOfRowsInserted++;

                //Close
                statement.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return numberOfRowsInserted;
    }

    @Override
    public int insertIngredients(Ingredient[] ingredients) {
        int result;
        int numberOfRowsInserted = 0;
        int lengthOfTheGivenArray = ingredients.length;
        String queryPrefix = "insert into Ingredients values";

        // log the method call
        System.out.println("--- insertIngredients ---");

        // for all given items
        for (int i = 0; i < lengthOfTheGivenArray; i++) {
            // create the query to be executed
            String query = queryPrefix + "('" +
                    ingredients[i].getIngredientID()+ "','" +
                    ingredients[i].getIngredientName() + "')";

            // try to execute the query
            try {
                Statement statement = this.con.createStatement();

                // execute the query
                result = statement.executeUpdate(query);
                // print the result
                System.out.println(result);
                numberOfRowsInserted++;

                //Close
                statement.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return numberOfRowsInserted;
    }

    @Override
    public int insertIncludes(Includes[] includes) {
        int result;
        int numberOfRowsInserted = 0;
        int lengthOfTheGivenArray = includes.length;
        String queryPrefix = "insert into Includes values";

        // log the method call
        System.out.println("--- insertIngredients ---");

        // for all given items
        for (int i = 0; i < lengthOfTheGivenArray; i++) {
            // create the query to be executed
            String query = queryPrefix + "('" +
                    includes[i].getItemID()+ "','" +
                    includes[i].getIngredientID() + "')";

            // try to execute the query
            try {
                Statement statement = this.con.createStatement();

                // execute the query
                result = statement.executeUpdate(query);
                // print the result
                System.out.println(result);
                numberOfRowsInserted++;

                //Close
                statement.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return numberOfRowsInserted;
    }

    @Override
    public int insertDietaryCategories(DietaryCategory[] categories) {
        int result;
        int numberOfRowsInserted = 0;
        int lengthOfTheGivenArray = categories.length;
        String queryPrefix = "insert into DietaryCategories values";

        // log the method call
        System.out.println("--- insertCategories ---");

        // for all given items
        for (int i = 0; i < lengthOfTheGivenArray; i++) {
            // create the query to be executed
            String query = queryPrefix + "('" +
                    categories[i].getIngredientID()+ "','" +
                    categories[i].getDietaryCategory() + "')";

            // try to execute the query
            try {
                Statement statement = this.con.createStatement();

                // execute the query
                result = statement.executeUpdate(query);
                // print the result
                System.out.println(result);
                numberOfRowsInserted++;

                //Close
                statement.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return numberOfRowsInserted;
    }

    @Override
    public int insertRatings(Rating[] ratings) {
        int result;
        int numberOfRowsInserted = 0;
        int lengthOfTheGivenArray = ratings.length;
        String queryPrefix = "insert into Ratings values";

        // log the method call
        System.out.println("--- insertRatings ---");

        // for all given items
        for (int i = 0; i < lengthOfTheGivenArray; i++) {
            // create the query to be executed
            String query = queryPrefix + "('" +
                    ratings[i].getRatingID()+ "','" +
                    ratings[i].getItemID() + "','" +
                    ratings[i].getRating() + "','" +
                    ratings[i].getRatingDate() + "')";

            // try to execute the query
            try {
                Statement statement = this.con.createStatement();

                // execute the query
                result = statement.executeUpdate(query);
                // print the result
                System.out.println(result);
                numberOfRowsInserted++;

                //Close
                statement.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return numberOfRowsInserted;
    }

    @Override
    public MenuItem[] getMenuItemsWithGivenIngredient(String name) {
        // ResultSet: A table of data representing a database result set, which is usually generated by executing a statement that queries the database.
        ResultSet resultSet;
        ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();
        MenuItem[] menuItemsArray = null;

        // log the method call
        System.out.println(String.format("--- getMenuItemsWithGivenIngredient(String name: %s) ---", name));

        // write your query
        String query = "select M.itemID as itemID, M.itemName as itemName, M.cuisine as cuisine, M.price as price from MenuItems M, Ingredients I, Includes Inc where " +
                "(I.ingredientName =" + " '" + name + "')" + " and " +
                "(Inc.ingredientID = I.ingredientID)" + " and " +
                "(M.itemID = Inc.itemID) " +
                "ORDER BY M.itemID ASC;";

        try {
            Statement statement = this.con.createStatement();
            resultSet = statement.executeQuery(query);

            // loop in the result set (table)
            while (resultSet.next() != false) {
                // get the field values of the current record in the table
                int m_itemID = resultSet.getInt("itemID");
                String m_itemName = resultSet.getString("itemName");
                String m_cuisine = resultSet.getString("cuisine");
                int m_price = resultSet.getInt("price");

                // create a temporary MenuItem
                MenuItem currentMenuItem = new MenuItem(m_itemID, m_itemName, m_cuisine, m_price);

                // add this record to the menuItems list
                menuItems.add(currentMenuItem);
            }

            // convert array list to menuItem array
            menuItemsArray = menuItems.toArray(new MenuItem[0]);

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return menuItemsArray;
    }

    @Override
    public MenuItem[] getMenuItemsWithoutAnyIngredient() {
        ResultSet resultSet;
        ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();
        MenuItem[] menuItemsArray = null;

        // log the method call
        System.out.println("--- getMenuItemsWithoutAnyIngredient() ---");

        // write your query
        String query = "select M.itemID as itemID, M.itemName as itemName, M.cuisine as cuisine, M.price as price from MenuItems M where M.itemID NOT IN (select I.itemID from Includes I) ORDER BY itemID ASC;";

        try {
            Statement statement = this.con.createStatement();
            resultSet = statement.executeQuery(query);

            // loop in the result set (table)
            while (resultSet.next() != false) {
                // get the field values of the current record in the table
                int m_itemID = resultSet.getInt("itemID");
                String m_itemName = resultSet.getString("itemName");
                String m_cuisine = resultSet.getString("cuisine");
                int m_price = resultSet.getInt("price");

                // create a temporary MenuItem
                MenuItem currentMenuItem = new MenuItem(m_itemID, m_itemName, m_cuisine, m_price);

                // add this record to the menuItems list
                menuItems.add(currentMenuItem);
            }

            // convert array list to menuItem array
            menuItemsArray = menuItems.toArray(new MenuItem[0]);

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return menuItemsArray;
    }

    @Override
    public Ingredient[] getNotIncludedIngredients() {
        ResultSet resultSet;
        ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
        Ingredient[] ingredientsArray = null;

        // log the method call
        System.out.println("--- getNotIncludedIngredients() ---");

        // write your query
        String query = "select I.ingredientID as ingredientID, I.ingredientName as ingredientName from Ingredients I where I.ingredientID NOT IN (select I.ingredientID from Includes I) ORDER BY ingredientID ASC;";

        try {
            Statement statement = this.con.createStatement();
            resultSet = statement.executeQuery(query);

            // loop in the result set (table)
            while (resultSet.next() != false) {
                // get the field values of the current record in the table
                int m_ingredientID = resultSet.getInt("ingredientID");
                String m_ingredientName = resultSet.getString("ingredientName");

                // create a temporary MenuItem
                Ingredient currentIngredient = new Ingredient(m_ingredientID, m_ingredientName);

                // add this record to the menuItems list
                ingredients.add(currentIngredient);
            }

            // convert array list to menuItem array
            ingredientsArray = ingredients.toArray(new Ingredient[0]);

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ingredientsArray;
    }

    @Override
    public MenuItem getMenuItemWithMostIngredients() {
        ResultSet resultSet;
        MenuItem menuItemWithMostIngredients = null;

        // log the method call
        System.out.println("--- getMenuItemWithMostIngredients() ---");

        // write your query
        String query = "select * from MenuItems M where M.itemID = (select I0.itemID from Includes I0 group by I0.itemID having count(*) >= ALL (select count(*) from Includes I1 group by I1.itemID));";

        try {
            Statement statement = this.con.createStatement();
            resultSet = statement.executeQuery(query);

            // point to the first and the only record in our result table
            resultSet.next();

            // get the field values of the current record in the table
            int m_itemID = resultSet.getInt("itemID");
            String m_itemName = resultSet.getString("itemName");
            String m_cuisine = resultSet.getString("cuisine");
            int m_price = resultSet.getInt("price");

            // create a temporary MenuItem
            menuItemWithMostIngredients = new MenuItem(m_itemID, m_itemName, m_cuisine, m_price);

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return menuItemWithMostIngredients;
    }

    @Override
    public QueryResult.MenuItemAverageRatingResult[] getMenuItemsWithAvgRatings() {
        ResultSet resultSet;
        ArrayList<QueryResult.MenuItemAverageRatingResult> menuItemsWithAvgRatings = new ArrayList<QueryResult.MenuItemAverageRatingResult>();
        QueryResult.MenuItemAverageRatingResult[] menuItemsArray = null;

        // log the method call
        System.out.println("--- getMenuItemsWithAvgRatings() ---");

        // write your query
        String query = "SELECT M.itemID AS itemID, M.itemName AS itemName, AVG(R.rating) AS avgRating " +
                "FROM MenuItems M NATURAL LEFT OUTER JOIN Ratings R " +
                "GROUP BY M.itemID " +
                "ORDER BY avgRating DESC;";

        try {
            Statement statement = this.con.createStatement();
            resultSet = statement.executeQuery(query);

            // loop in the result set (table)
            while (resultSet.next() != false) {
                // get the field values of the current record in the table
                String m_itemID = resultSet.getString("itemID");
                String m_itemName = resultSet.getString("itemName");
                String m_avgRating = resultSet.getString("avgRating");

                // create a temporary MenuItem
                QueryResult.MenuItemAverageRatingResult currentMenuItem = new QueryResult.MenuItemAverageRatingResult(m_itemID, m_itemName, m_avgRating);

                // add this record to the menuItems list
                menuItemsWithAvgRatings.add(currentMenuItem);
            }

            // convert array list to menuItem array
            menuItemsArray = menuItemsWithAvgRatings.toArray(new QueryResult.MenuItemAverageRatingResult[0]);

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return menuItemsArray;
    }

    @Override
    public MenuItem[] getMenuItemsForDietaryCategory(String category) {
        ResultSet resultSet;
        ArrayList<MenuItem> menuItems = new ArrayList<MenuItem>();
        MenuItem[] menuItemsArray = null;

        // log the method call
        System.out.println(String.format("--- getMenuItemsForDietaryCategory(String category = %s) ---", category));

        // write your query
        String query = "SELECT DISTINCT M0.itemID as itemID, M0.itemName as itemName, M0.cuisine as cuisine, M0.price as price " +
                "FROM MenuItems M0, Includes I0 " +
                "WHERE M0.itemID = I0.itemID AND M0.itemID NOT IN (SELECT M.itemID as itemID FROM MenuItems M, Includes I WHERE M.itemID = I.itemID AND I.ingredientID IN (SELECT DISTINCT D0.ingredientID as ingredientID FROM DietaryCategories D0 WHERE D0.ingredientID NOT IN (SELECT D1.ingredientID as ingredientID FROM DietaryCategories D1 WHERE D1.dietaryCategory = '" + category + "'))) ORDER BY itemID ASC";

        try {
            Statement statement = this.con.createStatement();
            resultSet = statement.executeQuery(query);

            // loop in the result set (table)
            while (resultSet.next() != false) {
                // get the field values of the current record in the table
                int m_itemID = resultSet.getInt("itemID");
                String m_itemName = resultSet.getString("itemName");
                String m_cuisine = resultSet.getString("cuisine");
                int m_price = resultSet.getInt("price");

                // create a temporary MenuItem
                MenuItem currentMenuItem = new MenuItem(m_itemID, m_itemName, m_cuisine, m_price);

                // add this record to the menuItems list
                menuItems.add(currentMenuItem);
            }

            // convert array list to menuItem array
            menuItemsArray = menuItems.toArray(new MenuItem[0]);

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return menuItemsArray;
    }

    @Override
    public Ingredient getMostUsedIngredient() {
        ResultSet resultSet;
        Ingredient mostUsedIngredient = null;

        // log the method call
        System.out.println("--- getMostUsedIngredient() ---");

        // write your query
        String query = "select * from Ingredients I where I.ingredientID IN (select I0.ingredientID from Includes I0 group by I0.ingredientID having count(*) >= ALL (select count(*) from Includes I1 group by I1.ingredientID));";

        try {
            Statement statement = this.con.createStatement();
            resultSet = statement.executeQuery(query);

            // point to the first and the only record in our result table
            resultSet.next();

            // get the field values of the current record in the table
            int m_ingredientID = resultSet.getInt("ingredientID");
            String m_ingredientName = resultSet.getString("ingredientName");

            // create a temporary MenuItem
            mostUsedIngredient = new Ingredient(m_ingredientID, m_ingredientName);

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return mostUsedIngredient;
    }

    @Override
    public QueryResult.CuisineWithAverageResult[] getCuisinesWithAvgRating() {
        ResultSet resultSet;
        ArrayList<QueryResult.CuisineWithAverageResult> cuisinesWithAvgRatings = new ArrayList<QueryResult.CuisineWithAverageResult>();
        QueryResult.CuisineWithAverageResult[] cuisinesArray = null;

        // log the method call
        System.out.println("--- getCuisinesWithAvgRating() ---");

        // write your query
        String query = "SELECT M.cuisine as cuisine, AVG(R.rating) as avgRating " +
                "FROM MenuItems M NATURAL LEFT OUTER JOIN Ratings R " +
                "GROUP BY M.cuisine " +
                "ORDER BY avgRating DESC;";

        try {
            Statement statement = this.con.createStatement();
            resultSet = statement.executeQuery(query);

            // loop in the result set (table)
            while (resultSet.next() != false) {
                // get the field values of the current record in the table
                String m_cuisine = resultSet.getString("cuisine");
                String m_cuisineAvgRating = resultSet.getString("avgRating");

                // create a temporary MenuItem
                QueryResult.CuisineWithAverageResult currentMenuItem = new QueryResult.CuisineWithAverageResult(m_cuisine, m_cuisineAvgRating);

                // add this record to the menuItems list
                cuisinesWithAvgRatings.add(currentMenuItem);
            }

            // convert array list to menuItem array
            cuisinesArray = cuisinesWithAvgRatings.toArray(new QueryResult.CuisineWithAverageResult[0]);

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cuisinesArray;
    }

    @Override
    public QueryResult.CuisineWithAverageResult[] getCuisinesWithAvgIngredientCount() {
        ResultSet resultSet;
        ArrayList<QueryResult.CuisineWithAverageResult> cuisinesWithAverageResult = new ArrayList<QueryResult.CuisineWithAverageResult>();
        QueryResult.CuisineWithAverageResult[] cuisinesWithAverageResultArray = null;

        // log the method call
        System.out.println("--- getCuisinesWithAvgIngredientCount() ---");

        // write your query
        String query = "SELECT T.cuisine as cuisine, AVG(T.ingredientCount) as avgIngredientCount " +
                "FROM (SELECT M.cuisine as cuisine, M.itemID as itemID, COUNT(I.ingredientID) as ingredientCount FROM MenuItems M NATURAL LEFT OUTER JOIN Includes I GROUP BY M.cuisine, M.itemID) as T " +
                "GROUP BY T.cuisine ORDER BY avgIngredientCount DESC;";

        try {
            Statement statement = this.con.createStatement();
            resultSet = statement.executeQuery(query);

            // loop in the result set (table)
            while (resultSet.next() != false) {
                // get the field values of the current record in the table
                String m_cuisine = resultSet.getString("cuisine");
                String m_cuisineAvgRating = resultSet.getString("avgIngredientCount");

                // create a temporary MenuItem
                QueryResult.CuisineWithAverageResult currentMenuItem = new QueryResult.CuisineWithAverageResult(m_cuisine, m_cuisineAvgRating);

                // add this record to the menuItems list
                cuisinesWithAverageResult.add(currentMenuItem);
            }

            // convert array list to menuItem array
            cuisinesWithAverageResultArray = cuisinesWithAverageResult.toArray(new QueryResult.CuisineWithAverageResult[0]);

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cuisinesWithAverageResultArray;
    }

    @Override
    public int increasePrice(String ingredientName, String increaseAmount) {
        int numberOfRowsUpdated = 0;

        // log the method call
        System.out.println(String.format("--- increasePrice(String ingredientName = %s, String increaseAmount = %s) ---", ingredientName, increaseAmount));

        // write your query
        String query = "UPDATE MenuItems M, Includes I, Ingredients Ing " +
                "SET M.price = M.price + " + increaseAmount + " " +
                "WHERE M.itemID = I.itemID AND I.ingredientID = Ing.ingredientID AND Ing.ingredientName = " + "'" + ingredientName + "';";

        // try to execute the query
        try {
            Statement statement = this.con.createStatement();

            // execute the query
            numberOfRowsUpdated = statement.executeUpdate(query);

            // print the result
            System.out.println(numberOfRowsUpdated);

            //Close
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return numberOfRowsUpdated;
    }

    @Override
    public Rating[] deleteOlderRatings(String date) {
        ResultSet resultSet;
        ArrayList<Rating> ratings = new ArrayList<Rating>();
        Rating[] ratingsArray = null;

        // log the method call
        System.out.println(String.format("--- deleteOlderRatings(String date = %s) ---", date));

        // write your query to get the records that will be deleted
        String query1 = "SELECT * FROM Ratings R WHERE R.ratingDate < '" + date + "' ORDER BY R.ratingID ASC;";
        // write your query to delete the records
        String query2 = "DELETE FROM Ratings R WHERE R.ratingDate < '" + date + "';";

        try {
            Statement statement = this.con.createStatement();
            resultSet = statement.executeQuery(query1);

            // loop in the result set (table)
            while (resultSet.next() != false) {
                // get the field values of the current record in the table
                int m_ratingID = resultSet.getInt("ratingID");
                int m_itemID = resultSet.getInt("itemID");
                int m_rating = resultSet.getInt("rating");
                String m_ratingDate = resultSet.getString("ratingDate");

                // create a temporary rating
                Rating currentRating = new Rating(m_ratingID, m_itemID, m_rating, m_ratingDate);

                // add this record to the menuItems list
                ratings.add(currentRating);
            }

            // convert array list to menuItem array
            ratingsArray = ratings.toArray(new Rating[0]);

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            Statement statement = this.con.createStatement();
            statement.executeUpdate(query2);

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ratingsArray;
    }
}
