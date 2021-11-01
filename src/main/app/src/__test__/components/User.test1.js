import { render, screen } from '@testing-library/react';
import { shallow } from 'enzyme';
import { Provider } from "react-redux";

import configureMockStore from "redux-mock-store";

import User from '../../components/User';

const mockStore = configureMockStore();
const store = mockStore({});

test("renders without crashing", () => {
  const component = shallow(
    <Provider store={store}>
  <User />
  </Provider>);
    // then
    expect(component.getElements()).toMatchSnapshot();
});
