package poc.companies.database;

import java.time.Instant;
import java.util.UUID;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import poc.companies.common.dto.CompanyMap;
import poc.companies.common.dto.CompanyMapBase;
import poc.companies.common.dto.CompanySearchMap;
import poc.companies.common.dto.PagedListMap;
import poc.companies.common.enums.CompanyStatusType;
import poc.companies.database.external.exceptions.CompanyNotFoundException;
import poc.companies.database.internal.database.CompanyService;

/**
 *
 * @author crijk
 */
public class CompanyServiceTest {

    public CompanyServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of CreateCompany method, of class CompanyService.
     */
    @Test
    public void testCreateCompany() {
        System.out.println("CreateCompany");
        CompanyService instance = new CompanyService();
        CompanyMapBase company = new CompanyMapBase("Company name", CompanyStatusType.Enabled, "platform");

        Instant before = Instant.now();
        CompanyMap result = instance.CreateCompany(company);
        Instant after = Instant.now();

        assertTrue(result.getId() > 0);
        assertEquals(company.getStatusType(), result.getStatusType());
        assertEquals(company.getName(), result.getName());
        assertEquals(company.getPlatform(), result.getPlatform());
        TestUtils.assertInRange(before, result.getCreateDateTime(), after);

        try {
            CompanyMap lookup = instance.GetCompany(result.getId());
            assertEquals(result, lookup);
        } catch (CompanyNotFoundException ex) {
            assertNull(ex);
        }
    }

    /**
     * Test of UpdateCompany method, of class CompanyService.
     */
    @Test
    public void testUpdateCompany() {
        CompanyMapBase company = new CompanyMapBase("Company name", CompanyStatusType.Enabled, "platform");
        CompanyMapBase updated = new CompanyMapBase("new name", CompanyStatusType.Disabled, "new platform");

        CompanyService instance = new CompanyService();
        CompanyMap result = instance.CreateCompany(company);
        try {
            boolean done = instance.UpdateCompany(result.getId(), updated);
            CompanyMap lookup = instance.GetCompany(result.getId());

            assertTrue(done);
            assertEquals(updated.getStatusType(), lookup.getStatusType());
            assertEquals(updated.getName(), lookup.getName());
            assertEquals(updated.getPlatform(), lookup.getPlatform());

        } catch (CompanyNotFoundException ex) {
            assertNull(ex);
        }
    }

    /**
     * Test of GetCompany method, of class CompanyService.
     */
    @Test
    public void testGetCompany_NotFound_ReturnsNull() {
        System.out.println("GetCompany");
        CompanyService instance = new CompanyService();
        try {
            CompanyMap result = instance.GetCompany(Long.MAX_VALUE);
            assertNotNull(result);
            fail("look up should have thrown an error");
        } catch (CompanyNotFoundException ex) {
            assertNotNull(ex);
        }
    }

    @Test
    public void testSearchCompanies_UnknownName_ReturnsEmpty() {
        System.out.println("GetCompany");
        CompanyService instance = new CompanyService();
        PagedListMap<CompanyMap> result = instance.SearchCompanies(new CompanySearchMap("!!name!!", null, null, null, null));
        assertNotNull(result);
        assertEquals(0, result.getTotal());
    }

    @Test
    public void testSearchCompanies() {
        String name1 = UUID.randomUUID().toString();
        String name2 = UUID.randomUUID().toString();
        String platform1 = UUID.randomUUID().toString();
        String platform2 = UUID.randomUUID().toString();

        CompanyService instance = new CompanyService();
        CompanyMap company1 = instance.CreateCompany(new CompanyMapBase(name1, CompanyStatusType.Enabled, platform1));
        CompanyMap company2 = instance.CreateCompany(new CompanyMapBase(name2, CompanyStatusType.Disabled, platform2));

        PagedListMap<CompanyMap> searchAll = instance.SearchCompanies(new CompanySearchMap(null, null, null, null, null));
        PagedListMap<CompanyMap> searchName = instance.SearchCompanies(new CompanySearchMap(name1, null, null, null, null));
        PagedListMap<CompanyMap> searchEnabled = instance.SearchCompanies(new CompanySearchMap(null, true, null, null, null));
        PagedListMap<CompanyMap> searchDisabled = instance.SearchCompanies(new CompanySearchMap(null, false, null, null, null));
        PagedListMap<CompanyMap> searchPlatform = instance.SearchCompanies(new CompanySearchMap(null, null, platform1, null, null));
        PagedListMap<CompanyMap> searchMulti = instance.SearchCompanies(new CompanySearchMap(name1, true, platform1, null, null));

        assertTrue(searchAll.getTotal() >= 2);
        assertTrue(searchAll.getList().contains(company1));
        assertTrue(searchAll.getList().contains(company2));

        assertTrue(searchName.getTotal() >= 1);
        assertTrue(searchName.getList().contains(company1));
        assertFalse(searchName.getList().contains(company2));

        assertTrue(searchEnabled.getTotal() >= 1);
        assertTrue(searchEnabled.getList().contains(company1));
        assertFalse(searchEnabled.getList().contains(company2));

        assertTrue(searchDisabled.getTotal() >= 1);
        assertFalse(searchDisabled.getList().contains(company1));
        assertTrue(searchDisabled.getList().contains(company2));

        assertTrue(searchPlatform.getTotal() >= 1);
        assertTrue(searchPlatform.getList().contains(company1));
        assertFalse(searchPlatform.getList().contains(company2));

        assertTrue(searchMulti.getTotal() >= 1);
        assertTrue(searchMulti.getList().contains(company1));
        assertFalse(searchMulti.getList().contains(company2));
    }
}
