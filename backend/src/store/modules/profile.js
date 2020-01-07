const profile = {

  state: {
    swichInfo: {
      swiName: ''
    }
  },

  getters: {
    getSwiName(state) {
      return state.swichInfo
    }
  },

  mutations: {
    setSwiName(state, info) {
      state.swichInfo.swiName = info;
    }
  },

  actions: {
    asyncSetSwiName(context, info) {
      context.commit('setSwiName', info);
    }
  }
}

export default profile;
