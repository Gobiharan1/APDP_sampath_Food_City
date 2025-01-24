package ui;

import org.junit.Before;
import org.junit.Test;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import com.toedter.calendar.JDateChooser;
import java.sql.Date;

import static org.junit.Assert.*;

public class AdminTest {

    private Admin admin; // Instance of the Admin class
    private JTable table;
    private JTextField totalAmountField;
    private JDateChooser dateChooser;
    private JTextField annualSalesField;
    private JTextField dailySalesTextField;
    private JTable btable;
    private JTable jTable1;
    private JTable jTable2;
    private Login login;
    private JTextField monthlySales; // JTextField for displaying total monthly sales
    private JDateChooser dateStart;  // Start date for the monthly report
    private JDateChooser dateEnd;    // End date for the monthly report
    private JTextField yearField; // Input field for the year
    
  
    @Before
    public void setUp() {
        admin = new Admin(); // Initialize the Admin class
        table = new JTable(new DefaultTableModel(new Object[]{"Product Name", "Customer ID", "Quantity", "Total Sales"}, 0)); // Mock JTable
        totalAmountField = new JTextField(); // Mock JTextField
        dateChooser = new JDateChooser(); // Mock JDateChooser
        btable = new JTable(new DefaultTableModel(new Object[]{"Product", "Quantity Sold"}, 0)); // Mock JTable
        dailySalesTextField = new JTextField();
        jTable1 = new JTable(new DefaultTableModel(new Object[]{"Product Name", "Quantity", "Unit Price", "Total Sold"}, 0)); // Mock JTable
        login = new Login();
        monthlySales = new JTextField();  // Corrected to JTextField
        dateStart = new JDateChooser();  // Corrected for start date
        dateEnd = new JDateChooser();    // Corrected for end date

        // Set up mock data for jTable2 (monthly sales table)
        jTable2 = new JTable(new DefaultTableModel(new Object[]{"Product Name", "Quantity", "Unit Price", "Total Sold"}, 0));
        annualSalesField = new JTextField();
        yearField = new JTextField();

    }

    /**
     * Test of branch method with valid branch name.
     */
    @Test
    public void testBranch_ValidBranch() throws SQLException {
        String branchName = "Matara"; // Example valid branch name

        // Call the branch method
        admin.Branch(branchName, table, totalAmountField);

        DefaultTableModel model = (DefaultTableModel) table.getModel();

        // Verify that the table contains rows
        assertTrue("The table should contain rows for a valid branch name", model.getRowCount() > 0);

        // Verify that the totalAmountField is populated
        assertFalse("The total amount field should not be empty for a valid branch name", totalAmountField.getText().isEmpty());

        // Verify that the total amount is a positive number
        double totalAmount = Double.parseDouble(totalAmountField.getText());
        assertTrue("The total amount should be greater than 0", totalAmount > 0);
    }

    /**
     * Test of branch method with invalid branch name.
     */
    @Test
    public void testBranch_InvalidBranch() throws SQLException {
        String branchName = "InvalidBranch"; // Example invalid branch name

        // Call the branch method
        admin.Branch(branchName, table, totalAmountField);

        DefaultTableModel model = (DefaultTableModel) table.getModel();

        // Verify that the table contains no rows
        assertEquals("The table should contain no rows for an invalid branch name", 0, model.getRowCount());

        // Verify that the totalAmountField is empty
        assertEquals("The total amount field should be empty for an invalid branch name", "", totalAmountField.getText());
    }

    /**
     * Test of branch method with null branch name.
     */
    @Test
    public void testBranch_NullBranch() throws SQLException {
        String branchName = ""; // Null branch name

        // Call the branch method
        admin.Branch(branchName, table, totalAmountField);

        DefaultTableModel model = (DefaultTableModel) table.getModel();

        // Verify that the table contains no rows
        assertEquals("The table should contain no rows for a null branch name", 0, model.getRowCount());

        // Verify that the totalAmountField is empty
        assertEquals("The total amount field should be empty for a null branch name", "", totalAmountField.getText());
    }

    @Test
    public void testBestSAlproduct_ValidDate() {
        // Set up a valid date in the JDateChooser
        java.util.Date validDate = java.sql.Date.valueOf("2022-12-31");
        dateChooser.setDate(validDate);

        // Call the bestSAlproduct method
        admin.bestSAlproduct(dateChooser, btable);

        DefaultTableModel model = (DefaultTableModel) btable.getModel();

        // Verify that the table contains rows
        assertTrue("The table should contain rows for a valid date", model.getRowCount() > 0);

        // Verify that the data in the table matches expected values (assuming example values for test verification)
        assertEquals("The first product should be the best-selling product", "Yogurt", model.getValueAt(0, 0));
        assertTrue("The quantity sold should be a positive number", (int) model.getValueAt(0, 1) > 0);
    }

    @Test
    public void testBestSAlproduct_NoDate() {
        // Ensure no date is selected in the JDateChooser
        dateChooser.setDate(null);

        // Call the bestSAlproduct method
        admin.bestSAlproduct(dateChooser, btable);

        DefaultTableModel model = (DefaultTableModel) btable.getModel();

        // Verify that the table contains no rows
        assertEquals("The table should contain no rows if no date is selected", 0, model.getRowCount());

        // Verify that an appropriate message dialog is shown
        // Note: JOptionPane dialogs are tricky to test, but ensure the method behaves correctly
    }

    @Test
    public void testBestSAlproduct_InvalidDate() {
        // Set up a date with no sales data
        java.util.Date noDataDate = java.sql.Date.valueOf("2026-01-01");
        dateChooser.setDate(noDataDate);

        // Call the bestSAlproduct method
        admin.bestSAlproduct(dateChooser, btable);

        DefaultTableModel model = (DefaultTableModel) btable.getModel();

        // Verify that the table contains no rows
        assertEquals("The table should contain no rows for a date with no sales data", 0, model.getRowCount());
    }

    @Test
    public void testDaily_ValidDate() throws SQLException {
        java.util.Date validDate = java.sql.Date.valueOf("2025-01-01");
        dateChooser.setDate(validDate);

        admin.daily(dateChooser, jTable1, dailySalesTextField);

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

        assertTrue("The table should contain rows for a valid date", model.getRowCount() > 0);
        assertFalse("The daily sales field should not be empty for a valid date", dailySalesTextField.getText().isEmpty());
        double dailySales = Double.parseDouble(dailySalesTextField.getText());
        assertTrue("The daily sales amount should be greater than 0", dailySales > 0);
    }

    @Test
    public void testDaillyemptyDate() throws SQLException {
        dateChooser.setDate(null);

        // Instead of expecting an exception, check that an error message is displayed
        // If JOptionPane.showMessageDialog is used, consider using a mock or spy for verification
        admin.daily(dateChooser, jTable1, dailySalesTextField);

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        assertEquals("The table should contain no rows if no date is selected", 0, model.getRowCount());
        assertEquals("The daily sales field should be empty if no date is selected", "", dailySalesTextField.getText());
    }

    @Test
    public void testDaily_Invalid() throws SQLException {
        java.util.Date noDataDate = java.sql.Date.valueOf("2026-01-01");
        dateChooser.setDate(noDataDate);

        admin.daily(dateChooser, jTable1, dailySalesTextField);

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        assertEquals("The table should contain no rows for a date with no sales data", 0, model.getRowCount());
        assertEquals("The daily sales field should be empty for a date with no sales data", "", dailySalesTextField.getText());
    }

    @Test
    public void testLogin_ValidAdmin() throws SQLException {
        String username = "James";
        String password = "123";
        String userType = "admin";

        // Call the login method
        String result = login.login(username, password, userType);

        // Verify that the login method returns success
        assertEquals("success", result);

        // Further assertions can be made to check UI behavior, 
        // for example, whether the Admin dashboard is shown
        // This requires additional mock setup or checking UI changes.
    }

    @Test
    public void testLogin_Empty() throws SQLException {
        String username = "";
        String password = "";
        String userType = "";

        // Call the static login method directly
        String result = login.login(username, password, userType);

        // Verify that the login method returns empty fields
        assertEquals("empty fields", result);
    }

    @Test
    public void testLogin_InvalidCredentials() throws SQLException {
        String username = "InvalidUser";
        String password = "WrongPassword";
        String userType = "admin";

        // Call the login method
        String result = login.login(username, password, userType);

        // Verify that the login method returns an error message for invalid credentials
        assertEquals("invalid credentials", result);
    }

    @Test
    public void testMonthly_ValidDateRange() throws SQLException {
        // Set valid start and end dates (for example: January 2023)
        dateStart.setDate(Date.valueOf("2023-01-01"));
        dateEnd.setDate(Date.valueOf("2023-01-31"));

        // Call the monthly method to generate the report
        admin.monthly(dateStart, dateEnd, jTable2, monthlySales);

        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();

        // Assert that the table contains rows for the valid date range
        assertTrue("The table should contain rows for a valid date range", model.getRowCount() > 0);
        assertTrue("The total sales field should not be empty for valid data", !monthlySales.getText().isEmpty());

    }

    @Test
    public void testMonthly_EmptyDateRange() throws SQLException {
        // Set no date for the start and end
        dateStart.setDate(null);
        dateEnd.setDate(null);

        // Call the monthly method to generate the report
        admin.monthly(dateStart, dateEnd, jTable2, monthlySales);

        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();

        // Assert that the table contains no rows for an empty date range
        assertEquals("The table should contain no rows for an empty date range", 0, model.getRowCount());

        // Assert that the total sales field is empty
        assertEquals("The total sales field should be empty for an empty date range", "", monthlySales.getText());
    }

    @Test
    public void testMonthly_InvalidDateRange() throws SQLException {
        // Set an invalid date range (start date after end date)
        dateStart.setDate(Date.valueOf("2026-02-01"));
        dateEnd.setDate(Date.valueOf("2026-01-31"));

        // Call the monthly method to generate the report
        admin.monthly(dateStart, dateEnd, jTable2, monthlySales);

        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();

        // Assert that the table contains no rows for an invalid date range
        assertEquals("The table should contain no rows for an invalid date range", 0, model.getRowCount());

        // Assert that the total sales field is empty
        assertEquals("The total sales field should be empty for an invalid date range", "", monthlySales.getText());
    }

@Test
    public void testAnnual_ValidYear() throws SQLException {
        // Set a valid year
        String validYear = "2022";
        yearField.setText(validYear);

        // Call the Annual method
        admin.Annual(yearField, annualSalesField, table);

        // Verify that the annual sales field is populated
        assertFalse("The annual sales field should not be empty for a valid year", annualSalesField.getText().isEmpty());

        // Verify that the annual sales value is a valid number and greater than 0
        double annualSales = Double.parseDouble(annualSalesField.getText());
        assertTrue("The annual sales should be greater than 0 for a valid year", annualSales > 0);

        // Verify that the table has rows populated
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        assertTrue("The table should contain rows for a valid year", model.getRowCount() > 0);
    }
    @Test
    public void testAnnual_EmptyYear() throws SQLException {
        // Leave the year input empty
        yearField.setText("");

        // Call the Annual method
        admin.Annual(yearField, annualSalesField, table);

        // Verify that the annual sales field is empty
        assertEquals("The annual sales field should be empty for an empty year", "", annualSalesField.getText());

        // Verify that no rows are added to the table
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        assertEquals("The table should be empty for an empty year", 0, model.getRowCount());
    }

    @Test
    public void testAnnual_InvalidYear() throws SQLException {
        // Set an invalid year (e.g., non-numeric or too long)
        String invalidYear = "12345";
        yearField.setText(invalidYear);

        // Call the Annual method
        admin.Annual(yearField, annualSalesField, table);

        // Verify that the annual sales field is empty
        assertEquals("The annual sales field should be empty for an invalid year", "", annualSalesField.getText());

        // Verify that no rows are added to the table
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        assertEquals("The table should be empty for an invalid year", 0, model.getRowCount());
    }

}
