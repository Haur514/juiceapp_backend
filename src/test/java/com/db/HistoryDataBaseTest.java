package com.db;

import org.dbunit.IDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.example.demo.repository.MemberRepository;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;

@SpringBootTest(classes = {MemberRepository.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
@DbUnitConfiguration(dataSetLoader = CsvDataSetLoader.class)
@TestExecutionListeners({
      DependencyInjectionTestExecutionListener.class,
      TransactionalTestExecutionListener.class,
      DbUnitTestExecutionListener.class
    })
public class HistoryDataBaseTest {
    static IDatabaseTester databaseTester;
    static IDatabaseConnection connection;

}
