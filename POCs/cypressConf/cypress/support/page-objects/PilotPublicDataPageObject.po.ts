const PILOT_HEADER = 'pilot-header';

export class PilotPublicDataPageObject {
    public checkPilotHeader(target: string): void {
        cy.get('#pilot-header').contains(target);
    }
}
