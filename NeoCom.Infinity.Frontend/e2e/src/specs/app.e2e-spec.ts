// - PROTRACTOR
import { browser } from 'protractor';
import { logging } from 'protractor';
// - COMPONENTS
import { LoginValidationPage } from '../pages/LoginValidation.page';
import { DashboardHomePage } from '../pages/DashboardHome.page';

describe('workspace-project App', () => {
    let page: LoginValidationPage;
    const pageIdentifier = 'Login Validation Page';

    beforeEach(() => {
        page = new LoginValidationPage();
    });

    it('should display welcome message', () => {
        page.navigateTo();
        expect(page.getPageName()).toEqual(pageIdentifier);
    });
    it('should display dashboard page', () => {
        // export class DashboardHomePage extends SharedPanelsElements {
        let dashboard: DashboardHomePage = new DashboardHomePage();
        let dashPageIdentifier = 'Dashboard Home Page';
        dashboard.navigateTo();
        expect(dashboard.getPageName()).toEqual(dashPageIdentifier);
    });

    afterEach(async () => {
        // Assert that there are no errors emitted from the browser
        const logs = await browser.manage().logs().get(logging.Type.BROWSER);
        expect(logs).not.toContain(jasmine.objectContaining({
            level: logging.Level.SEVERE,
        } as logging.Entry));
    });
});
