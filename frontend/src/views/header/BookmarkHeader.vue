<template>
  <v-navigation-drawer app permanent width="312px" floating>
    <v-row class="fill-height" no-gutters>
      <v-navigation-drawer mini-variant mini-variant-width="56" permanent>
        <!-- home icon -->
        <v-list-item class="px-2">
          <v-list-item-avatar>
            <v-img src="@/assets/images/TaggleLogo.png"></v-img>
          </v-list-item-avatar>
        </v-list-item>

        <!--user information button-->
        <template v-slot:append>
          <v-menu right offset-x>
            <template v-slot:activator="{ on, attrs }">
              <div class="text-center py-2">
                <v-avatar size="40" v-bind="attrs" v-on="on">
                  <img :src="user.picture" alt="user-profile" />
                </v-avatar>
              </div>
            </template>
            <v-list>
              <v-list-item
                href="https://chrome.google.com/webstore/detail/taggle/oacdjiemdacpldngplhlklpkeijpjijc"
                target="_blank"
              >
                <v-list-item-title>크롬 확장 설치하기</v-list-item-title>
              </v-list-item>
              <v-list-item @click.prevent="openUserGuideDialog">
                <v-list-item-title>사용법</v-list-item-title>
              </v-list-item>
              <v-list-item to="/profile">
                <v-list-item-title>프로필</v-list-item-title>
              </v-list-item>
              <v-list-item href="/oauth2/logout">
                <v-list-item-title>로그아웃</v-list-item-title>
              </v-list-item>
            </v-list>
          </v-menu>
        </template>
        <UserGuideDialog :userGuideDialog="guideDialog" :permanentClose="false" @close="guideDialog = false" />
      </v-navigation-drawer>
      <!-- tags & categories -->
      <TagNavigation />
    </v-row>
  </v-navigation-drawer>
</template>

<script>
import { FETCH_CATEGORIES } from '@/store/share/actionTypes.js';
import { USER } from '@/store/share/getterTypes.js';
import { mapActions, mapGetters } from 'vuex';
import TagNavigation from '@/views/header/component/TagNavigation.vue';
import UserGuideDialog from '@/views/header/component/UserGuideDialog.vue';

export default {
  name: 'BookmarkHeader',
  components: {
    UserGuideDialog,
    TagNavigation,
  },
  data() {
    return {
      guideDialog: false,
    };
  },
  methods: {
    ...mapActions([FETCH_CATEGORIES]),
    openUserGuideDialog() {
      this.guideDialog = true;
    },
  },
  computed: {
    ...mapGetters([USER]),
  },
};
</script>
