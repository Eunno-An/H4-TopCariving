import { Component, ReactNode } from 'react';
import { Flex, Text } from '.';

interface ErrorBoundaryProps {
  children: ReactNode;
}

interface ErrorBoundaryState {
  hasError: boolean;
}

export class ErrorBoundary extends Component<
  ErrorBoundaryProps,
  ErrorBoundaryState
> {
  constructor(props: ErrorBoundaryProps) {
    super(props);
    this.state = { hasError: false };
  }

  static getDerivedStateFromError() {
    return { hasError: true };
  }

  render() {
    if (this.state.hasError) {
      return (
        <Flex>
          <Flex direction="column" gap={10}>
            <Text typo="Heading3_Bold">잠시 후 다시 시도해주세요.</Text>
            <Flex direction="column" height="auto">
              <Text typo="Body3_Regular" palette="DarkGray">
                서버로부터 데이터를 불러오지 못했습니다
              </Text>
              <Text typo="Body3_Regular" palette="DarkGray">
                지속적으로 오류가 발생하는 경우 다시 새로고침해주세요.
              </Text>
            </Flex>
          </Flex>
        </Flex>
      );
    }
    return this.props.children;
  }
}
