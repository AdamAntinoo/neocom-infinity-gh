import { NeoComWebPOCPage } from './app.po';

describe('neo-com-web-poc App', () => {
  let page: NeoComWebPOCPage;

  beforeEach(() => {
    page = new NeoComWebPOCPage();
  });

  it('should display welcome message', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('Welcome to app!');
  });
});
