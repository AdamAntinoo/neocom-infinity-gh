import { NeocomWebPage } from './app.po';

describe('neocom-web App', () => {
  let page: NeocomWebPage;

  beforeEach(() => {
    page = new NeocomWebPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
