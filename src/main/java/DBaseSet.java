import org.sqlite.JDBC;

import java.sql.*;
import java.util.*;

public class DBaseSet {

    // Константа, в которой хранится адрес подключения
    private static final String CON_STR = "jdbc:sqlite:C:\\Users\\user\\Desktop\\ProjectForYA\\dataBaseSet.db";
    // В данный список будем загружать наши продукты, полученные из БД
    private List<ButtonsSet> buttonsSets= new ArrayList<ButtonsSet>();

    // Используем шаблон одиночка, чтобы не плодить множество
    // экземпляров класса DBaseSet
    private static DBaseSet instance = null;

    public static synchronized DBaseSet getInstance() throws SQLException {
        if (instance == null)
            instance = new DBaseSet();
        return instance;
    }
    // Объект, в котором будет храниться соединение с БД
    private Connection connection;

    private DBaseSet() throws SQLException {
        // Регистрируем драйвер, с которым будем работать
        // в нашем случае Sqlite
        DriverManager.registerDriver(new JDBC());
        // Выполняем подключение к базе данных
        this.connection = DriverManager.getConnection(CON_STR);
    }

    //получение кнопок из бд
    public List<ButtonsSet> getAllButtonsSet() {

        // Statement используется для того, чтобы выполнить sql-запрос
        try (Statement statement = this.connection.createStatement()) {
            // В данный список будем загружать наши продукты, полученные из БД
          //  List<ButtonsSet> buttonsSets= new ArrayList<ButtonsSet>();
            // В resultSet будет храниться результат нашего запроса,
            // который выполняется командой statement.executeQuery()
            ResultSet resultSet = statement.executeQuery("SELECT nameBut, imageBut, descriotion FROM buttons");
            // Проходимся по нашему resultSet и заносим данные в products
            while (resultSet.next()) {
                buttonsSets.add(new ButtonsSet(resultSet.getString("nameBut"),
                        resultSet.getString("imageBut"),
                        resultSet.getString("descriotion")));
            }
            // Возвращаем наш список
            return buttonsSets;

        } catch (SQLException e) {
            e.printStackTrace();
            // Если произошла ошибка - возвращаем пустую коллекцию
            return Collections.emptyList();
        }
    }

    // Добавление продукта в БД
    public void addProduct(ButtonsSet buttonS) {
        // Создадим подготовленное выражение, чтобы избежать SQL-инъекций

        /*
        buttons - название таблицы в базе данных
        далее название колонок
        далее устанавливаем значеня которые берём из передаваемой кнопки buttonS
         */
        try (PreparedStatement statement = this.connection.prepareStatement(
                "INSERT INTO buttons(`nameBut`, `imageBut`, `descriotion`) " +
                        "VALUES(?, ?, ?)")) {
            statement.setObject(1, buttonS.getName());
            statement.setObject(2, buttonS.getImagePath());
            statement.setObject(3, buttonS.getDescription());
            // Выполняем запрос
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Удаление продукта по name
    public void deleteProduct(String name) {
        try (PreparedStatement statement = this.connection.prepareStatement(
                "DELETE FROM buttons WHERE name = ?")) {
            statement.setObject(1, name);
            // Выполняем запрос
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
