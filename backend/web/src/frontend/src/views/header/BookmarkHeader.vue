<template>
  <v-navigation-drawer app permanent width="312px" floating>
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
          <!--          dialog 컴포넌트화로 인해서 기존 버튼과의 불일치 발생. 해결 필요-->
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

      <!-- tags & categories -->
      <tagList />
    </v-row>
  </v-navigation-drawer>
</template>

<script>
import BookmarkAddModal from '@/views/header/component/BookmarkAddModal.vue';
import TagList from '@/views/header/component/TagList.vue';

export default {
  name: 'BookmarkHeader',
  components: {
    BookmarkAddModal,
    TagList,
  },
};
</script>
