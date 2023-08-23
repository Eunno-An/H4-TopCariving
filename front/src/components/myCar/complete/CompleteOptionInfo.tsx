import styled from '@emotion/styled';
import { Flex, Text } from '@components/common';
import { CompleteOptionCard } from './CompleteOptionCard';
import { completeOptionValue } from '@pages/MyCar/Complete';

export const SelectOptionContainer = ({
  options,
}: {
  options: completeOptionValue[];
}) => {
  return (
    <>
      {options && (
        <Flex direction="column" width={1040} gap={10}>
          <Flex gap={21} justify="flex-start">
            <Text typo="Heading1_Medium">선택 옵션</Text>
            <Flex width="auto">
              <Text typo="Heading2_Medium" palette="Gold">
                {`${options.length}`}
              </Text>
              <Text typo="Heading2_Medium">개</Text>
            </Flex>
          </Flex>
          <SelectedOption justify="space-between" borderRadius="8px" gap={10}>
            {options.map(
              (
                { photoUrl, name, categoryDetail, price, childOptions },
                idx,
              ) => (
                <CompleteOptionCard
                  photoUrl={photoUrl}
                  optionName={name}
                  price={price}
                  optionDetail={categoryDetail}
                  key={`selectedOption_${idx}`}
                  childOptions={childOptions}
                />
              ),
            )}
          </SelectedOption>
        </Flex>
      )}
    </>
  );
};

const SelectedOption = styled(Flex)`
  flex-wrap: wrap;
`;
