// - PAGES
import { LoginValidationPage } from '../pages/LoginValidation.page';

export class AuthorizationWorld {
    private page: LoginValidationPage;
    private code: string;
    private state: string;

    public getPage(): LoginValidationPage {
        return this.page;
    }
    public setPage(loginValidationPage: LoginValidationPage) {
        this.page = loginValidationPage;
    }
    public getCode(): string {
        return this.code;
    }
    public setCode(requestCode: any) {
        this.code = requestCode;
    }
    public getState(): string {
        return this.state;
    }
    public setState(requestState: any) {
        this.state = requestState;
    }
}
