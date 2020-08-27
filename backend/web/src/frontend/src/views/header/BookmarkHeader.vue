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
              <v-list-item to="/profile">
                <v-list-item-title>Profile</v-list-item-title>
              </v-list-item>
              <v-list-item href="/oauth2/logout">
                <v-list-item-title>Logout</v-list-item-title>
              </v-list-item>
            </v-list>
          </v-menu>
        </template>
      </v-navigation-drawer>

      <!-- tags & categories -->
      <TagNavigation />
    </v-row>
  </v-navigation-drawer>
</template>

<script>
import { FETCH_CATEGORIES } from '@/store/share/actionTypes.js';
import { CATEGORIES, USER } from '@/store/share/getterTypes.js';
import { mapActions, mapGetters } from 'vuex';
import TagNavigation from '@/views/header/component/TagNavigation.vue';

export default {
  name: 'BookmarkHeader',
  components: {
    TagNavigation,
  },
  methods: {
    ...mapActions([FETCH_CATEGORIES]),
  },
  computed: {
    ...mapGetters([CATEGORIES, USER]),
  },
};
</script>
