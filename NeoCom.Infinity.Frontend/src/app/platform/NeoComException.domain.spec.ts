// - TESTING
import { TestBed } from '@angular/core/testing';
// - PROVIDERS
import { SupportIsolationService } from '@app/testing/SupportIsolation.service';
// - DOMAIN
import { NeoComException } from './NeoComException';

describe('CLASS NeoComException [Module: DOMAIN]', () => {
    let isolation: SupportIsolationService;

    beforeEach(() => {
        isolation = TestBed.get(SupportIsolationService);
    });
    // - C O N S T R U C T I O N   P H A S E
    describe('Construction Phase', () => {
        it('Should be created', () => {
            console.log('><[domain/NeoComException]> should be created');
            expect(new NeoComException()).toBeTruthy();
        });
        it('Fields should be on initial state', () => {
            const exception = new NeoComException();
            expect(exception.status).toBe(0);
            expect(exception.statusText).toBe('Undefined Exception');
            expect(exception.allowsRetry).toBeFalsy();
            expect(exception.message).toBe('Request undefined exception:0:Undefined Exception.');
            expect(exception.userMessage).toBeUndefined();
        });
    });
    // - C O D E   C O V E R A G E   P H A S E
    describe('Code Coverage Phase [getters]', () => {
        it('NeoComException.getUserMessage.success: check the user message when not defined field', () => {
            const expected = isolation.generateRandomString(32);
            const exception = new NeoComException({ message: expected })
            const obtained = exception.getUserMessage();
            expect(obtained).toBe(expected);
        });
        it('NeoComException.getUserMessage.success: check the user message when defined field', () => {
            const expectedMessage = isolation.generateRandomString(32);
            const expected = isolation.generateRandomString(32);
            const exception = new NeoComException({ message: expectedMessage, userMessage: expected })
            const obtained = exception.getUserMessage();
            expect(obtained).toBe(expected);
        });
    });
    describe('Code Coverage Phase [setters]', () => {
        it('NeoComException.setUserMessage: check the user message contents', () => {
            const expected = isolation.generateRandomString(32);
            const exception = new NeoComException();
            exception.setUserMessage(expected);
            const obtained = exception.getUserMessage();
            expect(obtained).toBe(expected);
        });
        it('NeoComException.setRetryable: check the exception retry flag', () => {
            const exception = new NeoComException();
            exception.setRetryable(true);
            const obtained = exception.getUserMessage();
            expect(obtained).toBeTruthy();
        });
    });
});
