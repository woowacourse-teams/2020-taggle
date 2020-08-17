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

        <!-- navigation menus -->
        <v-list dense nav>
          <!-- dialog 컴포넌트화로 인해서 기존 버튼과의 불일치 발생. 해결 필요 -->
          <!--          <v-list-item link>-->
          <!--            <v-list-item-action>-->
          <!--              <v-icon>local_offer</v-icon>-->
          <!--            </v-list-item-action>-->
          <!--            <v-list-item-content>-->
          <!--              <v-list-item-title>tags</v-list-item-title>-->
          <!--            </v-list-item-content>-->
          <!--          </v-list-item>-->
          <BookmarkAddModal />
        </v-list>

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

      <v-navigation-drawer permanent>
        <!-- tag search bar -->
        <v-text-field
          prepend-inner-icon="mdi-magnify"
          placeholder="Tag Search"
          filled
          rounded
          dense
          class="ma-2"
          hide-details="true"
        ></v-text-field>

        <!-- tags & categories -->
        <v-list v-for="{ id, title, tags } in categories" :key="id" class="grow">
          <v-list-group class="text-left" link value="true">
            <template v-slot:activator>
              <v-list-item-content>
                <v-list-item-title class="font-weight-black text-h7">
                  {{ title }}
                </v-list-item-title>
              </v-list-item-content>
            </template>
            <v-list-item v-for="{ id, name } in tags" :key="id" :to="{ name: 'bookmarks', params: { id } }">
              <v-list-item-content>
                {{ name }}
              </v-list-item-content>
            </v-list-item>
          </v-list-group>
        </v-list>
        <!-- category modify buttons -->
        <template v-slot:append>
          <v-divider></v-divider>
          <v-row width="100%">
            <v-col class="text-right">
              <CategoryAddModal></CategoryAddModal>
              <CategoryTagModifyModal></CategoryTagModifyModal>
            </v-col>
          </v-row>
        </template>
      </v-navigation-drawer>
    </v-row>
  </v-navigation-drawer>
</template>

<script>
import CategoryAddModal from '@/views/header/component/CategoryAddModal.vue';
import CategoryTagModifyModal from '@/views/header/component/TagEditModal.vue';
import BookmarkAddModal from '@/views/header/component/BookmarkAddModal.vue';
import { FETCH_CATEGORIES } from '@/store/share/actionTypes.js';
import { CATEGORIES } from '@/store/share/getterTypes.js';
import { mapActions, mapGetters } from 'vuex';

export default {
  name: 'BookmarkHeader',
  components: {
    CategoryAddModal,
    CategoryTagModifyModal,
    BookmarkAddModal,
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
