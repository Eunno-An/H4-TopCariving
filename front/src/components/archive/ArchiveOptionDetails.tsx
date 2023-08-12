import { Flex } from '@components/common';
import styled from '@emotion/styled';
import { OptionCard } from '@components/archive/archiveOptionCard/OptionCard';

export interface OptionInfoInterface {
  photoUrl: string;
  optionName: string;
  option?: string[];
  review?: string;
  tags: string[];
}

const optionInfoDummy = [
  {
    photoUrl:
      'https://topcariving.s3.ap-northeast-2.amazonaws.com/selected/2_cooling.jpeg',
    optionName: '컴포트 || 패키지 ',
    option: [
      '후석 승객 알림',
      '메탈 리어범퍼스텝',
      '메탈 도어스커프',
      '3열 파워 폴딩시트',
      '3열 열선시트',
      '헤드업 디스플레이',
    ],
    review:
      '승차감이 좋아요 차가 크고 운전하는 시야도 높아서 좋았어요 저는 13개월 아들이 있는데 뒤에 차시트 달아도 널널할 것 같습니다.',
    tags: ['어린이👶', '편리해요😉', '출퇴근용으로 딱🚶'],
  },
  {
    photoUrl:
      'https://topcariving.s3.ap-northeast-2.amazonaws.com/convenience/wirelesscharging.jpeg',
    optionName: '컴포트 || 패키지 ',
    review:
      '승차감이 좋아요 차가 크고 운전하는 시야도 높아서 좋았어요 저는 13개월 아들이 있는데 뒤에 차시트 달아도 널널할 것 같습니다.',
    tags: ['어린이👶', '편리해요😉', '출퇴근용으로 딱🚶'],
  },
  {
    photoUrl:
      'https://topcariving.s3.ap-northeast-2.amazonaws.com/multimedia/reartalking.jpeg',
    optionName: '컴포트 || 패키지 ',
    option: [
      '후석 승객 알림',
      '메탈 리어범퍼스텝',
      '메탈 도어스커프',
      '3열 파워 폴딩시트',
      '3열 열선시트',
      '헤드업 디스플레이',
    ],
    tags: ['어린이👶', '편리해요😉', '출퇴근용으로 딱🚶'],
  },
  {
    photoUrl:
      'https://topcariving.s3.ap-northeast-2.amazonaws.com/selected/2_cooling.jpeg',
    optionName: '컴포트 || 패키지 ',

    review:
      '승차감이 좋아요 차가 크고 운전하는 시야도 높아서 좋았어요 저는 13개월 아들이 있는데 뒤에 차시트 달아도 널널할 것 같습니다.',
    tags: ['어린이👶', '편리해요😉', '출퇴근용으로 딱🚶'],
  },
  {
    photoUrl:
      'https://topcariving.s3.ap-northeast-2.amazonaws.com/selected/metalrearbumper.jpeg',
    optionName: '컴포트 || 패키지 ',
    option: [
      '후석 승객 알림',
      '메탈 리어범퍼스텝',
      '메탈 도어스커프',
      '3열 파워 폴딩시트',
      '3열 열선시트',
      '헤드업 디스플레이',
    ],
    review:
      '승차감이 좋아요 차가 크고 운전하는 시야도 높아서 좋았어요 저는 13개월 아들이 있는데 뒤에 차시트 달아도 널널할 것 같습니다.',
    tags: ['어린이👶', '편리해요😉', '출퇴근용으로 딱🚶'],
  },
  {
    photoUrl:
      'https://topcariving.s3.ap-northeast-2.amazonaws.com/selected/2_cooling.jpeg',
    optionName: '컴포트 || 패키지 ',
    tags: ['어린이👶', '편리해요😉', '출퇴근용으로 딱🚶'],
  },
  {
    photoUrl:
      'https://topcariving.s3.ap-northeast-2.amazonaws.com/N_performance/20_darkwheel.jpeg',
    optionName: '컴포트 || 패키지 ',
    option: [
      '후석 승객 알림',
      '메탈 리어범퍼스텝',
      '메탈 도어스커프',
      '3열 파워 폴딩시트',
      '3열 열선시트',
      '헤드업 디스플레이',
    ],
    review:
      '승차감이 좋아요 차가 크고 운전하는 시야도 높아서 좋았어요 저는 13개월 아들이 있는데 뒤에 차시트 달아도 널널할 것 같습니다.',
    tags: ['어린이👶', '편리해요😉', '출퇴근용으로 딱🚶'],
  },
  {
    photoUrl:
      'https://topcariving.s3.ap-northeast-2.amazonaws.com/selected/2_cooling.jpeg',
    optionName: '컴포트 || 패키지 ',
    tags: ['어린이👶', '편리해요😉', '출퇴근용으로 딱🚶'],
  },
  {
    photoUrl:
      'https://topcariving.s3.ap-northeast-2.amazonaws.com/selected/2_cooling.jpeg',
    optionName: '컴포트 || 패키지 ',
    option: [
      '후석 승객 알림',
      '메탈 리어범퍼스텝',
      '메탈 도어스커프',
      '3열 파워 폴딩시트',
      '3열 열선시트',
      '헤드업 디스플레이',
    ],

    tags: ['어린이👶', '편리해요😉', '출퇴근용으로 딱🚶'],
  },
  {
    photoUrl:
      'https://topcariving.s3.ap-northeast-2.amazonaws.com/selected/2_cooling.jpeg',
    optionName: '컴포트 || 패키지 ',

    review:
      '승차감이 좋아요 차가 크고 운전하는 시야도 높아서 좋았어요 저는 13개월 아들이 있는데 뒤에 차시트 달아도 널널할 것 같습니다.',
    tags: ['어린이👶', '편리해요😉', '출퇴근용으로 딱🚶'],
  },
] as OptionInfoInterface[];

export const ArchiveOptionDetails = () => {
  return (
    <Flex backgroundColor="White">
      <Flex width={1024} justify="space-between" padding="40px 0 0 0" gap={24}>
        <CardContainer>
          {optionInfoDummy.map(
            (it, idx) => idx % 3 == 0 && <OptionCard info={it} />,
          )}
        </CardContainer>
        <CardContainer>
          {optionInfoDummy.map(
            (it, idx) => idx % 3 == 1 && <OptionCard info={it} />,
          )}
        </CardContainer>
        <CardContainer>
          {optionInfoDummy.map(
            (it, idx) => idx % 3 == 2 && <OptionCard info={it} />,
          )}
        </CardContainer>
      </Flex>
    </Flex>
  );
};

const CardContainer = styled(Flex)`
  flex-direction: column;
  gap: 24px;
  justify-content: flex-start;
`;
