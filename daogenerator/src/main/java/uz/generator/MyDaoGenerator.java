package uz.generator;


import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;


public class MyDaoGenerator {

    public static void main(String[] args) throws Exception {
        Schema schema=new Schema(1, "uz.ishborApp.Entity");
        schema.enableKeepSectionsByDefault();
        Entity search =schema.addEntity("Search");
        search.addIdProperty().autoincrement().primaryKey();
        search.addStringProperty("word");
        search.addDateProperty("created");

        Entity category=schema.addEntity("Category");
        category.addIdProperty().primaryKey();
        category.addStringProperty("title");
        category.addStringProperty("link");
        category.addIntProperty("jobQuantity");

        Entity vacancy=schema.addEntity("Vacancy");
        vacancy.addIdProperty().primaryKey();
        vacancy.addStringProperty("price");
        vacancy.addStringProperty("companyName");
        vacancy.addStringProperty("position");
        vacancy.addStringProperty("descc");
        vacancy.addStringProperty("stDate");
        vacancy.addStringProperty("html");

        Property categoryId=vacancy.addLongProperty("categoryId").notNull().getProperty();
        vacancy.addToOne(category, categoryId);
        category.addToMany(vacancy, categoryId);

        new DaoGenerator().generateAll(schema, "/home/sherxon/androidProjects/ishBorApp/app/src/main/java/uz/ishborApp/Entity");
    }
}
