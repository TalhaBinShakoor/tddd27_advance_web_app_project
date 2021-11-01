import { render, screen } from '@testing-library/react';
import { shallow } from 'enzyme';

import Footer from '../../components/Footer';

test("renders without crashing", () => {
  const component = shallow(<Footer />);
  // then
  expect(component.getElements()).toMatchSnapshot();
});
