const validator = {
  bookmark: {
    url: [
      (v) => !!v || 'URL값은 필수입니다.',
      (v) =>
        /^(https?):\/\/[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]$/.test(v) || 'URL은 유효한 형태여야 합니다.',
    ],
  },
  category: {
    title: [(v) => !!v || 'title값은 필수입니다.'],
    changeTitle: [(v) => !!v || '변경할 카테고리를 지정해주세요.'],
  },
};

export default validator;
