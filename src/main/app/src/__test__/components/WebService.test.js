import { render, screen } from '@testing-library/react';
import { shallow } from 'enzyme';
import { Provider } from "react-redux";

import configureMockStore from "redux-mock-store";

import WebService from '../../components/WebService';

const mockStore = configureMockStore();
const store = mockStore({});

test("renders without crashing", () => {
  const component = shallow(
   <Provider store={store}>
  <WebService />
  </Provider>);
    // then
    expect(component.getElements()).toMatchSnapshot();
});

//https://www.toptal.com/react/tdd-react-unit-testing-enzyme-jest
//it("should create an entry in component state", () => 
