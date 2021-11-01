import { render, screen } from '@testing-library/react';
import { shallow } from 'enzyme';

import App from '../App';

test("renders without crashing", () => {
  //shallow(<App />);
  const component = shallow(<App />);
    // then
    expect(component.getElements()).toMatchSnapshot();
});
