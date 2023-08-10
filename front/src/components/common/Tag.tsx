import styled from '@emotion/styled';
import { Flex, Text } from '.';

export const Tag = ({ desc }: { desc: string }) => {
  return (
    <TagContainer>
      <Text typo="Body3_Regular">{desc}</Text>
    </TagContainer>
  );
};

const TagContainer = styled(Flex)`
  width: auto;
  height: 22px;
  padding: 12px;

  border-radius: 8px;
  background: #f6f3f2;

  white-space: nowrap;
`;
