package vending_machine.application.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import vending_machine.application.port.out.ProductPort;
import vending_machine.application.port.out.TransactionPort;
import vending_machine.domain.Product;
import vending_machine.domain.Transaction;
import vending_machine.domain.exception.InsufficientFundsException;
import vending_machine.domain.exception.ProductNotAvailableException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VendingMachineServiceTest {

    @Mock
    private ProductPort productPort;

    @Mock
    private TransactionPort transactionPort;

    @InjectMocks
    private VendingMachineService vendingMachineService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPurchaseProductSuccess() {
        Product product = new Product(1L, "Soda", 1000, 10);
        when(productPort.loadProductByName("Soda")).thenReturn(product);

        Transaction transaction = vendingMachineService.purchaseProduct("Soda", 2200);

        assertNotNull(transaction);
        assertEquals(product, transaction.getProduct());
        assertEquals(2200, transaction.getAmountPaid());
        verify(transactionPort, times(1)).saveTransaction(any(Transaction.class));
        verify(productPort, times(1)).decreaseProductQuantity(product);
    }

    @Test
    void testPurchaseProductInsufficientFunds() {
        Product product = new Product(1L, "Soda", 1000, 10);
        when(productPort.loadProductByName("Soda")).thenReturn(product);

        assertThrows(InsufficientFundsException.class, () -> {
            vendingMachineService.purchaseProduct("Soda", 500);
        });
        verify(transactionPort, never()).saveTransaction(any(Transaction.class));
    }

    @Test
    void testPurchaseProductNotAvailable() {
        Product product = new Product(1L, "Soda", 1000, 0);
        when(productPort.loadProductByName("Soda")).thenReturn(product);

        assertThrows(ProductNotAvailableException.class, () -> {
            vendingMachineService.purchaseProduct("Soda", 1200);
        });
        verify(transactionPort, never()).saveTransaction(any(Transaction.class));
    }

    @Test
    void testRestockProduct() {
        Product product = new Product(1L, "Soda", 1000, 5);
        when(productPort.loadProductById(1L)).thenReturn(product);

        vendingMachineService.restockProduct(1L, 10);

        verify(productPort, times(1)).updateProductQuantity(product, 10);
    }

    @Test
    void testCheckInventory() {
        List<Product> productList = Arrays.asList(new Product(1L, "Soda", 1000, 5));
        when(productPort.findAll()).thenReturn(productList);

        List<Product> result = vendingMachineService.checkInventory();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Soda", result.get(0).getProductName());
    }

    @Test
    void testSaveProduct() {
        Product product = new Product(1L, "Soda", 1000, 10);

        vendingMachineService.saveProduct(product);

        verify(productPort, times(1)).saveProduct(product);
    }

    @Test
    void testGetTotalMoney() {
        when(transactionPort.getTotalMoney()).thenReturn(5000);

        int totalMoney = vendingMachineService.getTotalMoney();

        assertEquals(5000, totalMoney);
        verify(transactionPort, times(1)).getTotalMoney();
    }
}