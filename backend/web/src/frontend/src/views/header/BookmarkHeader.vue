<template>
  <v-navigation-drawer app permanent width="312px">
    <v-row class="fill-height" no-gutters>
      <v-navigation-drawer dark mini-variant mini-variant-width="56" permanent>
        <!-- home icon -->
        <v-list-item class="px-2">
          <v-list-item-avatar>
            <v-img src="@/assets/images/TaggleLog.png"></v-img>
          </v-list-item-avatar>
        </v-list-item>
        <v-divider></v-divider>

        <!--user information button-->
        <template v-slot:append>
          <div class="text-center">
            <v-avatar>
              <img
                src="https://avatars3.githubusercontent.com/u/45934117?s=460&u=32a2980278056093510eface6c7ddfca6bcd8bc9&v=4"
                alt="evan"
              />
            </v-avatar>
          </div>
        </template>
      </v-navigation-drawer>

      <!-- tags & categories -->
      <TagList />
    </v-row>
  </v-navigation-drawer>
</template>

<script>
import { FETCH_CATEGORIES } from '@/store/share/actionTypes.js';
import { CATEGORIES } from '@/store/share/getterTypes.js';
import { mapActions, mapGetters } from 'vuex';
import TagList from '@/views/header/component/TagList.vue';

export default {
  name: 'BookmarkHeader',
  components: {
    TagList,
  },
  created() {
    this[FETCH_CATEGORIES]();
  },
  methods: {
    ...mapActions([FETCH_CATEGORIES]),
  },
  computed: {
    ...mapGetters([CATEGORIES]),
  },
};
</script>
