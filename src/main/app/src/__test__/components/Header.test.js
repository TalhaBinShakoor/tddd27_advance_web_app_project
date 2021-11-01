import { render, screen } from '@testing-library/react';
import { shallow } from 'enzyme';
import { Provider } from "react-redux";

import configureMockStore from "redux-mock-store";


import Header from '../../components/Header';

const mockStore = configureMockStore();
const store = mockStore({});

test("renders without crashing", () => {
  const component = shallow(
    <Provider store={store}>
  <Header />
  </Provider>);
  // then
  expect(component.getElements()).toMatchSnapshot();
});
