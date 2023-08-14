import styled from '@emotion/styled';
import { Flex, Text } from '@components/common';
import { CompleteOptionCard } from './CompleteOptionCard';

export const SelectOptionContainer = () => {
  return (
    <Flex direction="column" width={1040} gap={10}>
      <Flex gap={21} justify="flex-start">
        <Text typo="Heading1_Medium">선택 옵션</Text>
        <Flex width="auto">
          <Text typo="Heading2_Medium" palette="Gold">
            {`${result.selectOptions.length + 1}`}
          </Text>
          <Text typo="Heading2_Medium">개</Text>
        </Flex>
      </Flex>
      <SelectedOption justify="space-between" borderRadius="8px" gap={10}>
        {result.selectOptions.map(
          ({ photoUrl, optionName, details, price }) => (
            <CompleteOptionCard
              photoUrl={photoUrl}
              optionName={optionName}
              price={price}
              details={details}
            />
          ),
        )}
      </SelectedOption>
    </Flex>
  );
};

const SelectedOption = styled(Flex)`
  flex-wrap: wrap;
`;

const result = {
  archivingId: 1,
  photoUrl: 'https://www.test.com/car.png',
  model: {
    carOptionId: 0,
    optionName: 'string',
    price: 0,
  },
  engine: {
    carOptionId: 0,
    optionName: 'string',
    price: 0,
  },
  bodyType: {
    carOptionId: 0,
    optionName: 'string',
    price: 0,
  },
  drivingMethod: {
    carOptionId: 0,
    optionName: 'string',
    price: 0,
  },
  exteriorColor: {
    carOptionId: 11,
    optionName: '어비스블랙펄',
    price: 0,
    photoUrl: 'https://www.test.com/black.png',
  },
  interiorColor: {
    carOptionId: 11,
    optionName: '어비스블랙펄',
    price: 0,
    photoUrl: 'https://www.test.com/black.png',
  },
  selectOptions: [
    {
      carOptionId: 0,
      optionName: '컴포트 1',
      photoUrl:
        'https://topcariving.s3.ap-northeast-2.amazonaws.com/external/doorpocketlighting.jpeg',
      details: [
        '후석 승객 알림',
        '메탈 리어범퍼스텝',
        '메탈 도어스커프',
        '3열 폴딩시트',
        '헤드업 디스틀레이',
        '3열 열선시트',
      ],
      price: 0,
    },
    {
      carOptionId: 0,
      optionName: '컴포트 2',
      photoUrl:
        'https://topcariving.s3.ap-northeast-2.amazonaws.com/external/doorpocketlighting.jpeg',
      details: [
        '후석 승객 알림',
        '메탈 리어범퍼스텝',
        '메탈 도어스커프',
        '3열 폴딩시트',
        '헤드업 디스틀레이',
        '3열 열선시트',
      ],
      price: 0,
    },
    {
      carOptionId: 0,
      optionName: '컴포트 3',
      photoUrl:
        'https://topcariving.s3.ap-northeast-2.amazonaws.com/external/doorpocketlighting.jpeg',
      details: [
        '후석 승객 알림',
        '메탈 리어범퍼스텝',
        '메탈 도어스커프',
        '3열 폴딩시트',
        '헤드업 디스틀레이',
        '3열 열선시트',
      ],
      price: 0,
    },
    {
      carOptionId: 0,
      optionName: '컴포트 4',
      photoUrl:
        'https://topcariving.s3.ap-northeast-2.amazonaws.com/external/doorpocketlighting.jpeg',
      details: [
        '후석 승객 알림',
        '메탈 리어범퍼스텝',
        '메탈 도어스커프',
        '3열 폴딩시트',
        '헤드업 디스틀레이',
        '3열 열선시트',
      ],
      price: 0,
    },
    {
      carOptionId: 0,
      optionName: '컴포트 5',
      photoUrl:
        'https://topcariving.s3.ap-northeast-2.amazonaws.com/external/doorpocketlighting.jpeg',
      details: [
        '후석 승객 알림',
        '메탈 리어범퍼스텝',
        '메탈 도어스커프',
        '3열 폴딩시트',
        '헤드업 디스틀레이',
        '3열 열선시트',
      ],
      price: 0,
    },
  ],
};
