/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chargebee.qa.app.test;

import com.amazonaws.services.cloudformation.model.Replacement;
import com.chargebee.app.InitializerServlet;
import com.chargebee.app.models.BasicPlan;
import com.chargebee.app.models.Book;
import com.chargebee.app.models.IssueBook;
import com.chargebee.app.models.Mark;
import com.chargebee.app.models.PhoneNumber;
import com.chargebee.app.models.Student;
import com.chargebee.app.models.Tables;
import static com.chargebee.app.models.Tables.*;
import com.chargebee.app.models.TestTax;
import com.chargebee.app.models.TestUser;
import com.chargebee.app.models.Transaction;
import com.chargebee.app.models.base.SchJobBase;
import com.chargebee.app.models.base.TestTaxBase;
import com.chargebee.app.models.dyn_cols.AppJobType;
import com.chargebee.app.saas.analytics.helper.ResetAnalyticsData;
import com.chargebee.app.scheduler.JobContext;
import com.chargebee.app.scheduler.Scheduler;
import com.chargebee.app.scheduler.jobs.ResetAnalyticsSchedulerJob;
import com.chargebee.framework.data.CurrencyType;
import com.chargebee.framework.jooq.JQDerivedColumn;
import com.chargebee.framework.jooq.JQTableColumn;
import com.chargebee.framework.jooq.SqlUtils;
import com.chargebee.framework.metamodel.StringCol;
import com.chargebee.framework.pojo.PojoList;
import static com.chargebee.framework.util.DateConditionUtil.yesterday;
import com.chargebee.framework.util.DateUtils;
import static com.chargebee.framework.util.DateUtils.now;
import com.chargebee.qa.core.TestEnv;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Calendar;
import static java.util.Calendar.DAY_OF_MONTH;
import org.jooq.Condition;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SortField;
/**
 *
 * @author sudhan
 */
public class TestDB {
    
    /**
     * This method adds sample data to the TestUsers table 
     * in the cb_local_common database using a PojoList bulk operation
     */
    public static void populateTestUsers() throws Exception {
        PojoList<TestUser> recordList =new PojoList();
        TestUser tu1 = new TestUser();
        tu1.firstName("Petr").lastName("Cech")
           .company("Arsenal").phoneNumber("98092 43434").creditBalance(200);
        TestUser tu2 = new TestUser();
        tu2.firstName("Bernd").lastName("Leno")
           .company("Arsenal").phoneNumber("93483 13235").creditBalance(599);
        TestUser tu3 = new TestUser();
        tu3.firstName("Hector").lastName("Bellerin")
           .company("Arsenal").phoneNumber("83742 68123").creditBalance(399);
        TestUser tu4 = new TestUser();
        tu4.firstName("Raghav").lastName("Nandakumar")
           .company("Chargebee").phoneNumber("95433 14324").creditBalance(1299);
        TestUser tu5 = new TestUser();
        tu5.firstName("Mohamed").lastName("Sullaiman")
           .company("Chargebee").phoneNumber("83928 17635").creditBalance(1199);
        TestUser tu6 = new TestUser();
        tu6.firstName("Sudhan").lastName("S")
           .company("Chargebee").phoneNumber("78712 34356").creditBalance(12500);
        recordList.add(tu1); recordList.add(tu2); recordList.add(tu3);
        recordList.add(tu4); recordList.add(tu5); recordList.add(tu6);
        recordList.dbInsert();
        for(TestUser tu : recordList) {
            System.out.println(tu.toString());
        }
    }
    
    /**
     * Retrieves records using conditions and sort fields
     * Contains solutions to the exercises on retrieval of records
     * @throws Exception 
     */
    public static void retrieveRecords() throws Exception {
        TestUser t1 = qtest_users.dbFetchOne(69000000004l);
        System.out.println("The user with ID 69000000004 is: " + t1.toString());
        
        Condition cond1 = qtest_users.company.notEqual("Chargebee");
        PojoList<TestUser> notInChargebee = qtest_users.dbFetchList(cond1);
        System.out.println("The users who are not in Chargebee are: ");
        for(TestUser t : notInChargebee) {
            System.out.println(t.toString());
        }
        
        Condition cond2 = qtest_users.company.equal("Chargebee")
                .and(qtest_users.credit_balance.between(1000, 10000));
        PojoList<TestUser> chargebeeCreditBalance = qtest_users.dbFetchList(cond2);
        System.out.println("The employees of Chargebee with credit balance in the 1k-10k range are: ");
        for(TestUser t : chargebeeCreditBalance) {
            System.out.println(t.toString());
        } 
        
        String companyName = "Arsenal";
        Condition[] cond3 = new Condition[]{qtest_users.company.equal(companyName)};
        SortField[] sort1 = new SortField[]{qtest_users.id.asc()};
        TestUser t2 = qtest_users.dbFetchAny(cond3, sort1);
        System.out.println("The first user from the company named \'" + companyName + "\' is: " 
                + t2.toString());
    }
    
    /**
     * Updates records using conditions
     * Contains solutions to the exercises on updation of records
     * @throws Exception 
     */
    public static void updateRecords() throws Exception {
        Long id = 69000000003l; int newCreditBalance = 1500;
        qtest_users.credit_balance.dbUpdate(newCreditBalance, qtest_users.id.equal(id));
        
        qtest_users.credit_balance.dbUpdate(2500, qtest_users.credit_balance.equal(2000));
        
        String newPhoneNumber = "78236 98122";
        String firstName = "Petr", lastName = "Cech";
        qtest_users.phone_number.dbUpdate(newPhoneNumber, 
                qtest_users.first_name.equal(firstName).and(
                        qtest_users.last_name.equal(lastName)));
        
        Long id2 = 69000000005l;
        TestUser tu = qtest_users.dbFetchOne(id2);
        tu.firstName("Joe").lastName("Denly").phoneNumber("9876543210")
          .company("CB");
        tu.dbUpdate();
        
        /*
        PojoList<TestUser> users = qtest_users.dbFetchList();
        for(TestUser u : users) {
            u.dispName(u.firstName().concat(" " + u.lastName()));
        }
        users.dbUpdate();
        */
        
        qtest_users.credit_balance.dbUpdate(20000, null);
    }
    
    
    /**
     * Deletes records using conditions
     * Contains solutions to the exercises on deletion of records
     * @exception Exception
     */
    public static void deleteRecords() throws Exception {
        qtest_users.dbDelete(69000000005l);
        
        TestUser testUser = qtest_users.dbFetchOne(qtest_users.first_name.equal("Bernd")
                            .and(qtest_users.last_name.equal("Leno")));
        if(testUser.dbDelete()) {
            System.out.println("The user " + testUser.toString() + " was deleted!");
        }
        
        qtest_users.company.dbDelete(null);
    }
    
    /**
     * Inserts and updates values in a table with an enum column
     * @throws Exception 
     */
    public static void enumFields() throws Exception {
        TestUser u = new TestUser();
        u.firstName("Joe").lastName("Diego").company("MyComp").creditBalance(200);
        u.dbInsert();
        
        Condition cond = qtest_users.first_name.equal("Joe")
                .and(qtest_users.last_name.equal("Diego"));
        TestUser u2 = qtest_users.dbFetchOne(cond);
        Long id = u2.id();
        
        PhoneNumber pn = new PhoneNumber();
        pn.testUserId(id)
                .type(PhoneNumber.Type.WORK)
                .number("044-645372");
        pn.dbInsert();
        
        pn.testUserId(id)
                .type(PhoneNumber.Type.HOME)
                .number("0413-2622127");
        pn.dbInsert();
        
        pn.testUserId(id)
                .type(PhoneNumber.Type.MOBILE)
                .number("9807654321");
        pn.dbInsert();
        
        qphone_numbers.number.dbUpdate("0413-2622127", 
                qphone_numbers.type.equal(PhoneNumber.Type.WORK.i)
                .and(qphone_numbers.test_user_id.equal(id)));
        
        qphone_numbers.number.dbUpdate("044-645372", 
                qphone_numbers.type.equal(PhoneNumber.Type.HOME.i)
                .and(qphone_numbers.test_user_id.equal(id)));
    }
    
    /**
     * Inserts and updates values in a table with a Timestamp column
     * @throws Exception 
     */
    public static void timestamp() throws Exception {
        /*
        int numModified = qtest_users.last_modified.dbUpdate(Timestamp.valueOf("2018-07-30 00:00:00"), qtest_users.id.equal(69000000000l));
        System.out.println(numModified + " rows updated with new value of last_modified");
        
        numModified = qtest_users.user_created_on.dbUpdate(Timestamp.valueOf("2018-07-30 00:00:00"), qtest_users.id.equal(69000000001l));
        System.out.println(numModified + " rows updated with new value of user_created_on");
        
        PojoList<TestUser> users = qtest_users.dbFetchList(
                qtest_users.last_modified.greaterThan(Timestamp.valueOf("2015-03-13 00:00:00")));
        System.out.println("The records that have been modified after 2015-3-13 are: ");
        for(TestUser u : users) {
            System.out.println(u.toString());
        }
        */
    }

    public static void advancedColumns() throws Exception {
        BasicPlan bp = new BasicPlan();
        bp.code("plan001");
        bp.amount(5000);
        bp.currency(CurrencyType.US_DOLLARS);
        bp.totalAmountReceived(150000l);
        bp.hostedPageUrl("http://muricarox.com");
        bp.dbInsert();
        
        BasicPlan bp2 = qbasic_plans.dbFetchOne(qbasic_plans.code.equal("plan001"));
        System.out.println(bp2.toString());
    }
    
    public static void populateStudents() throws Exception {
        Student s = new Student();
        s.firstName("Petr"); s.lastName("Cech"); s.dob(Date.valueOf("1982-05-21")); s.gender("Male"); 
        s.rollNo(3); s.standard("7"); s.email("petr@arsenal.com");
        s.dbInsert(); System.out.println("Inserting record: " + s.toString() + "... "); 
        
        s.firstName("Bernd"); s.lastName("Leno"); s.dob(Date.valueOf("1994-04-12")); s.gender("Male"); 
        s.rollNo(1); s.standard("7"); s.email("leno@arsenal.com");
        s.dbInsert(); System.out.println("Inserting record: " + s.toString() + "... ");
        
        s.firstName("Mesut"); s.lastName("Ozil"); s.dob(Date.valueOf("1991-09-03")); s.gender("Male"); 
        s.rollNo(2); s.standard("7"); s.email("ozil@arsenal.com");
        s.dbInsert(); System.out.println("Inserting record: " + s.toString() + "... ");
        
        s.firstName("Aaron"); s.lastName("Ramsey"); s.dob(Date.valueOf("1991-01-30")); s.gender("Male"); 
        s.rollNo(1); s.standard("8"); s.email("aaron@arsenal.com");
        s.dbInsert(); System.out.println("Inserting record: " + s.toString() + "... ");
        
        s.firstName("Henrikh"); s.lastName("Mkhitaryan"); s.dob(Date.valueOf("1992-03-18")); s.gender("Male"); 
        s.rollNo(2); s.standard("8"); s.email("mkhi@arsenal.com");
        s.dbInsert(); System.out.println("Inserting record: " + s.toString() + "... ");
    }
    
    public static void populateMarks() throws Exception {
        Mark m1 = new Mark(); m1.studentId(71000000001l); m1.mark(67); m1.subjectCode("MA6151");
        Mark m2 = new Mark(); m2.studentId(71000000001l); m2.mark(78); m2.subjectCode("CS6152");
        Mark m3 = new Mark(); m3.studentId(71000000001l); m3.mark(46); m3.subjectCode("CS6153");
        
        Mark m4 = new Mark(); m4.studentId(71000000002l); m4.mark(79); m4.subjectCode("MA6151");
        Mark m5 = new Mark(); m5.studentId(71000000002l); m5.mark(98); m5.subjectCode("CS6152");
        Mark m6 = new Mark(); m6.studentId(71000000002l); m6.mark(67); m6.subjectCode("CS6153");
        
        PojoList<Mark> marks = new PojoList<>();
        marks.add(m1); marks.add(m2); marks.add(m3);
        marks.add(m4); marks.add(m5); marks.add(m6);
        marks.dbInsert();
    }
    
    public static void derivedColumns() throws Exception {
        //populateStudents(); populateMarks();
        
        // derived column to calculate a student's age using their DOB
        long millis = System.currentTimeMillis();  
        java.sql.Date currDate = new java.sql.Date(millis);  
        
        JQDerivedColumn name = new JQDerivedColumn(
                qstudents.first_name.concat(" ").concat(qstudents.last_name), "Full Name"
            ).$derivedFrom(qstudents);   
        JQDerivedColumn age = new JQDerivedColumn(
                SqlUtils.fnDateDiff(currDate, qstudents.dob).div(365), "Age"
            ).$derivedFrom(qstudents);
        Result res = SqlUtils.Sql().select(name, age).from(qstudents).fetch();
        System.out.println("Student age: ");
        for(int i = 0; i < res.size(); i++) {
            System.out.println(res.getValue(i, name) + ", " + res.getValue(i, age));
        }
        
        // derived column to calculate a student's total marks
        JQDerivedColumn total = new JQDerivedColumn(qmarks.mark.sum(), "total").$derivedFrom(qmarks);
        Result res2 = SqlUtils.Sql().select(name, total)
                                .from(qstudents).join(qmarks_using_qstudents)
                                .groupBy(qmarks.student_id).fetch();
        System.out.println("Student grades: ");
        for(int i = 0; i < res2.size(); i++) {
            String grade = "";
            int currTotal = res2.getValueAsInteger(i, total);
            if(currTotal >= 250) { grade = "A+";}
            else if(currTotal >= 200 && currTotal < 250) { grade = "A"; }
            else if(currTotal >= 150 && currTotal < 200) { grade = "B+"; }
            else if(currTotal >= 100 && currTotal < 150) { grade = "B"; }
            else { grade = "C"; }
            
            System.out.println(res.getValue(i, name) + ": " + grade);
        }
    }
    
    public static void columnAttributes() throws Exception {
        TestTax t = new TestTax();
        t.label("abc").rate(2.5).vatSecretKey("AJKSADKASDKD").config("");
        t.dbInsert();
    }
    
    public static void populateBooks() throws Exception {
        Book b = new Book(); 
        b.title("Harry Potter and the Prisoner of Azkaban");
        b.author("J K Rowling"); b.publication("Bloomsbury Books"); b.isIssued(false);
        b.dbInsert();
        
        b.title("Swami and Friends");
        b.author("R K Narayan"); b.publication("Indian Thought Publications");
        b.isIssued(false); b.dbInsert();
        
        b.title("Mindset: The New Psychology of Success");
        b.author("Carol Dweck"); b.publication("Penguin Books"); b.isIssued(false);
        b.dbInsert();
        
        b.title("The Power Of Habit");
        b.author("Charles Duhigg"); b.publication("Penguin Books"); b.isIssued(false);
        b.dbInsert();
        
        b.title("The Guns of Navarone");
        b.author("Alistair MacLean"); b.publication("Bloomsbury Books"); b.isIssued(false);
        b.dbInsert();
    }
    
    public static void populateIssueBooks() throws Exception {
        long millis = System.currentTimeMillis();  
        IssueBook i = new IssueBook();
        
        i.studentId(71000000000l); i.bookId(72000000003l);
        i.issueDate(new java.sql.Date(millis)); i.dueDate(Date.valueOf("2018-09-07"));
        i.dbInsert();
        
        i.studentId(71000000000l); i.bookId(72000000000l);
        i.issueDate(Date.valueOf("2018-08-31")); i.dueDate(Date.valueOf("2018-09-06"));
        i.dbInsert();
        
        i.studentId(71000000002l); i.bookId(72000000001l);
        i.issueDate(Date.valueOf("2018-08-16")); i.dueDate(Date.valueOf("2018-09-16"));
        i.dbInsert();
        
        i.studentId(71000000002l); i.bookId(72000000002l);
        i.issueDate(Date.valueOf("2018-09-01")); i.dueDate(Date.valueOf("2018-09-08"));
        i.dbInsert();
        
        i.studentId(71000000004l); i.bookId(72000000004l);
        i.issueDate(Date.valueOf("2018-08-12")); i.dueDate(Date.valueOf("2018-08-24"));
        i.dbInsert();
    }
    
    public static void joins() throws Exception {
        //populateStudents();
        //populateBooks();
        //populateIssueBooks();
        
        Field fullName = qstudents.first_name.concat(" ").concat(qstudents.last_name);
        
        // Count number of books borrowed by each student
        Field count = qissue_books.book_id.count();
        Result<Record> res1 = SqlUtils.Sql()
                                   .select(fullName, count)
                                   .from(qstudents)
                                   .leftOuterJoin(qissue_books_using_qstudents)
                                   .groupBy(qstudents.id)
                                   .orderBy(count.desc(), fullName.asc())
                                   .fetch();

        System.out.println("Number of books borrowed by each student: ");
        for(Record rec : res1) {
            System.out.println(rec.getValue(fullName) + ", " + rec.getValue(count));
        }
        
        // List the details of students who didn't borrow any book
        Result<Record> res2 = SqlUtils.Sql()
                                   .select(qstudents.id, fullName, qstudents.email)
                                   .from(qstudents)
                                   .leftOuterJoin(qissue_books_using_qstudents)
                                   .where(qissue_books.student_id.isNull())
                                   .fetch();

        System.out.println("\nStudents who didn't borrow any book: ");
        for(Record rec : res2) {
            System.out.println(rec.getValue(qstudents.id) + ", " + rec.getValue(fullName)
                    + ", " + rec.getValue(qstudents.email));
        }
        
        // Details of students who need to return their books in the next 3 days
        long currentMillis = System.currentTimeMillis(); 
        long one_day_millis = 86400 * 1000;
        Date today = new java.sql.Date(currentMillis);
        Date threeDaysLater = new java.sql.Date(currentMillis + (3 * one_day_millis));
        
        Result<Record> res3 = SqlUtils.Sql()
                                   .select(fullName, qstudents.email, 
                                           qissue_books.book_id, qissue_books.due_date)
                                   .from(qstudents)
                                   .join(qissue_books_using_qstudents)
                                   .where(qissue_books.due_date.greaterOrEqual(today)
                                           .and(qissue_books.due_date.lessOrEqual(threeDaysLater)))
                                   .fetch();
        
        System.out.println("\nStudents who need to return their books in 3 days: ");
        for(Record rec : res3) {
            System.out.println(rec.getValue(fullName) + ", " + rec.getValue(qstudents.email)
            + ", " + rec.getValue(qissue_books.book_id) + ", " + rec.getValue(qissue_books.due_date));
        }
        
        // Fetch book and author name for books whose issue date is between two given dates
        Date date1 = Date.valueOf("2018-08-15"); Date date2 = Date.valueOf("2018-09-15");
        Result<Record> res4 = SqlUtils.Sql()
                                   .select(qbooks.title, qbooks.author, qissue_books.issue_date)
                                   .from(qbooks)
                                   .join(qissue_books_using_qbooks)
                                   .where(qissue_books.issue_date.between(date1, date2))
                                   .fetch();
        System.out.println("\nBooks with issue date between " + date1 + " and " + date2 + ": ");
        for(Record rec : res4) {
            System.out.println(rec.getValue(qbooks.title) + " by " + rec.getValue(qbooks.author)
                    + ", " + rec.getValue(qissue_books.issue_date));
        }
        
        // Find the ID and first name of the student who borrowed a given book
        String bookName = "Harry Potter and the Prisoner of Azkaban";
        Result<Record> res5 = SqlUtils.Sql()
                                   .select(qstudents.id, qstudents.first_name)
                                   .from(qissue_books)
                                   .join(qstudents_using_qissue_books)
                                   .join(qbooks_using_qissue_books)
                                   .where(qbooks.title.equal(bookName))
                                   .fetch();
        System.out.println("\nThe student who borrowed \'" + bookName + "\' is: ");
        for(Record rec : res5) {
            System.out.println(rec.getValue(qstudents.id) + ", " + rec.getValue(qstudents.first_name));
        }
    }
    
    public static void createObjectsFromResult() throws Exception {
        String bookName = "The Power Of Habit";
        Result<Record> res5 = SqlUtils.Sql()
                                   .select(qstudents.ALL)
                                   .select(qbooks.ALL)
                                   .from(qissue_books)
                                   .join(qstudents_using_qissue_books)
                                   .join(qbooks_using_qissue_books)
                                   .where(qbooks.title.equal(bookName))
                                   .fetch();
        
        PojoList<Student> students = qstudents.create(res5);
        PojoList<Book> books = qbooks.create(res5);

        System.out.println("Student and book details: ");
        for(Student s : students) System.out.println(s.toString());
        System.out.println();
        for(Book b : books) System.out.println(b.toString());    
    }
    
    public static void fetchRecordsAdditionalFunc() throws Exception {
        // Students whose names start with A or B
        System.out.println("The following students' names start with A or B: ");
        Result<Record> res = SqlUtils.Sql().select(qstudents.ALL)
                            .from(qstudents)
                            .where(qstudents.first_name.like("A%").or(qstudents.first_name.like("B%")))
                            .fetch();
        for(Record r : res) { 
            System.out.println(r.getValue(qstudents.first_name));
        }
        
        // Retrieve all the records from Students two at a time
        System.out.println("\nRecords from Students table, fetched 2 at a time: ");
        boolean moreRecords = true; int offset = 0, limit = 2;
        do {
            Result<Record> res2 = SqlUtils.Sql().select(qstudents.ALL)
                                 .from(qstudents)
                                 .limit(offset, limit)                
                                 .fetch();
            offset += limit;
            if(res2.size() == 0) {
                moreRecords = false;
            }
            else {
                for(Record r : res2) { 
                    Student s = qstudents.create(r); System.out.println(s.toString());
                }
                System.out.println("");
            }
        }
        while(moreRecords);
    }
    
    public static void updateDeleteRecords() throws Exception {
        int updatedRows = SqlUtils.Sql().update(qmarks)
                    .set(qmarks.mark, qmarks.mark.add(10))
                    .where(qmarks.mark.between(50, 70))
                    .execute();
    }
    
    public static void retrieveMultipleRecords() throws Exception {
        // Display marks in descending order for a given subject code
        String subjectCode = "CS6153";
        SortField<Integer> sort = qmarks.mark.desc();
        Condition cond = qmarks.mark.greaterThan(50).and(qmarks.subject_code.equal(subjectCode));
        
        PojoList<Mark> marks = qmarks.dbFetchAll(cond, sort);
        for(Mark m : marks) {
            System.out.println(m.toString());
        }
        
        // Retrieve details of all the books issued to a given student ID
        Student student = qstudents.dbFetchAny(qstudents.id.equal(71000000000l));
        PojoList<IssueBook> booksIssued = qissue_books.dbFetchIssueBooksForStudent(student);
        
        for(IssueBook ib : booksIssued) { 
            System.out.println(ib.toString());
        }
    }
    
    public static void bulkOperationsPojoList() throws Exception {
        PojoList<Mark> marks = qmarks.dbFetchAll();
        for(Mark m : marks) {
            m.mark(m.mark() - 5);
        }
        marks.dbUpdate();
    }
    
    public static void pojoListAdditionalMethods() throws Exception {
        //populateTestUsers();
        PojoList<TestUser> users = qtest_users.dbFetchAll(); 
        
        PojoList<TestUser> usersChargebee = users.filter(u -> u.company().equals("Chargebee"));
        int totalCreditChargebee = usersChargebee.sum(qtest_users.credit_balance);
        System.out.println("Total credit of Chargebee's employees: " + totalCreditChargebee);
        
        PojoList<TestUser> fnameNotNull = users.filter(u -> (u.firstName() != null && !u.firstName().isEmpty()));
        System.out.println("\nFirst names that aren't null: ");
        for(TestUser tu : fnameNotNull) System.out.println(tu.firstName() + " ");
        
        users.applyToAll(u -> u.dispName(u.firstName() + " " + u.lastName()));
        System.out.println("\nFull names of users: ");
        for(TestUser tu : fnameNotNull) System.out.println(tu.dispName());
        
        PojoList<TestUser> checkCreditBalance = users.filter(u -> u.creditBalance() > 500);
        if(checkCreditBalance == users) {
            System.out.println("\nYes, all users have their credit balance > 500!");
        }
        else {
            System.out.println("\nNo, not all users have their credit balance > 500!");
        }
    }
    
    public static void main(String[] args) throws Exception {
        /*
        TestEnv.initialize("mannar");
        JobContext jc = new JobContext(AppJobType.DAILY_CRUNCHING);
        Scheduler.scheduleAt(jc, now());
        
        TestEnv.initialize("merchant");
        JobContext jc1 = new JobContext(AppJobType.DAILY_CRUNCHING);
        Scheduler.scheduleAt(jc1, now());
        */ 
        InitializerServlet.initStandAlone();
        pojoListAdditionalMethods();
    }
}