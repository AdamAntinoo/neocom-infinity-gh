// - PROTRACTOR
import { by } from 'protractor';
import { element } from 'protractor';
import { browser } from 'protractor';
// - ASSERTION
import { expect } from 'chai';
import { assert } from 'chai';
import { IsolationService } from '../support/IsolationService.support';

const SERVER_NAME: string = 'server-name';
const SERVER_STATUS: string = 'server-status';
const SERVER_CAPSULEERS: string = 'server-capsuleers';

export class ServerInfoPanel extends IsolationService {

    public async validateServerInfoPanel(row: any): Promise<boolean> {
        let serverName = this.decodeDataTableRow(row, SERVER_NAME).toUpperCase();
        console.log('[ServerInfoPanel.validateServerInfoPanel]> SERVER_NAME=' + serverName);
        expect((await this.getServerName()).toUpperCase()).to.equal(serverName);
        let serverStatus = this.decodeDataTableRow(row, SERVER_STATUS).toUpperCase();
        console.log('[ServerInfoPanel.validateServerInfoPanel]> SERVER_STATUS=' + serverStatus);
        expect(await this.getServerStatus()).to.equal(serverStatus);
        let capsuleers = this.decodeDataTableRow(row, SERVER_CAPSULEERS);
        console.log('[AppInfoPanel.validateAppInfoPanel]> SERVER_CAPSULEERS=' + capsuleers);
        expect(await this.getServerCapsuleers()).to.equal(capsuleers);

        return Promise.resolve(true);
    }
    private getServerName(): Promise<string> {
        return element(by.css('#server-name')).getText() as Promise<string>;
    }
    private getServerStatus(): Promise<string> {
        return element(by.css('#server-status')).getText() as Promise<string>;
    }
    private getServerCapsuleers(): Promise<string> {
        return element(by.css('#server-capsuleers')).getText() as Promise<string>;
    }
}
