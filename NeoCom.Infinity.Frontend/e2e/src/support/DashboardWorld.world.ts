// - PAGES
import { DashboardHomePage } from '../pages/DashboardHome.page';

export class DashboardWorld {
    private page: DashboardHomePage;

    public getPage(): DashboardHomePage {
        return this.page;
    }
    public setPage(loginValidationPage: DashboardHomePage) {
        this.page = loginValidationPage;
    }
}
