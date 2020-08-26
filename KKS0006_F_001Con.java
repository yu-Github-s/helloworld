package jp.co.amiactive;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import jp.co.amiactive.model.KKS0006_F_001Model;

import jp.co.amiactive.model.KKS0006_S_001Model;
import jp.co.amiactive.model.KKS0006_S_002Model;
import jp.co.amiactive.service.KKS0006_F_001PageBackSer;
import jp.co.amiactive.service.KKS0006_F_001PageUpSer;
import jp.co.amiactive.service.KKS0006_F_001DeleteSer;
import jp.co.amiactive.service.KKS0006_F_001InitSer;
import jp.co.amiactive.service.KKS0006_F_001SearchSer;


/**
 * 賞与情報一覧画面のコントローラクラス
 * @author脇本
 */

@Controller
public class KKS0006_F_001Con {

	// 初期表示処理を行うサービスクラス"initService"
	@Autowired
	private KKS0006_F_001InitSer initService;

	/**
	 * 一覧画面の初期表示処理
	 * 
	 * @author 脇本
	 * @param Model
	 *            model（引数をmodelとしている）テンプレートから参照する変数名:KKS0006_F_001Model,オブジェクト名
	 *            :initService.init()
	 * @param HttpSession
	 *            session セッション管理（引数をsessionとしている）
	 * @return 遷移先（賞与情報一覧画面）のテンプレート名(syouyo_ichiran)を文字列としてreturnするので、
	 *         メソッドの戻り値の型はString型
	 */

	/*
	 * Spring MVC のコントローラに付与して、リクエスト URL に対して、どのメソッドが処理を実行するか定義する。
	 * 処理対象とするURLを指定(value = "/syouyo/ichiran") GET でアクセスされたリクエストを処理する
	 * (method =RequestMethod.GET)
	 */
	
	@RequestMapping(value = "/syouyo/ichiran", method = RequestMethod.GET)
	public String init(Model model, HttpSession session) {

		/*
		 * 初期表示処理 addAttribute()メソッドでView側へ渡すオブジェクトのデータを第一引数に
		 * テンプレートから参照する変数名[KKS0006_F_001Model]、第二引数にオブジェクト名[initService.init()]
		 * として格納する
		 */
		model.addAttribute("KKS0006_F_001Model", initService.init());

		/*
		 * 画面DTOの宣言(セッションをgetする) 返す値のデータ型(Object) メソッド:getAttribute(String)
		 * 引数:("KKS0006_S_001Model")
		 * 引数に指定されたデータ名に該当するセッションスコープのデータ値を返す。該当のデータ名がない場合にはnullを返す。
		 */
		KKS0006_F_001Model syouyo = (KKS0006_F_001Model) session.getAttribute("KKS0006_S_001Model");

		// セッションに値がある場合（詳細画面より「もどる」ボタン押下時）
		if (syouyo != null) {
			/*
			 * 戻るボタン押下後にセッションに値がある場合の処理
			 * 第一引数にテンプレートから参照する変数名[KKS0006_F_001Model]、第二引数にオブジェクト名[initService
			 * init(syouyo)]として格納したものをview側へ渡す
			 */
			model.addAttribute("KKS0006_F_001Model", initService.init(syouyo));
		}
		// "syouyo_ichiran"のJSPを呼び出す
		return "syouyo_ichiran";
	}

	// 検索ボタン
	// 検索処理を行うサービスクラス"serService"
	@Autowired
	private KKS0006_F_001SearchSer serService;

	/**
	 * 検索処理
	 * 
	 * @param search
	 *            画面に表示する項目を格納するDTO（KKS0006_F_001Model）型
	 * @param result
	 *            入力チェック処理をサービスクラス(serService)で行うので、「BindingResult
	 *            result」をサービスクラスへ渡す
	 * @param model
	 *            テンプレートから参照する変数名:KKS0006_F_001Model,オブジェクト名:fmodel(
	 *            検索処理と初期表示処理を詰めたもの)
	 * @param session
	 *            HttpSession型
	 *            セッション管理（検索結果の全件分のデータを格納するセッションクラス"KKS0006_S_001Model"を使用）
	 * @return "syouyo_ichiran"
	 *         遷移先（賞与情報一覧画面）のテンプレート名(syouyo_ichiran)を文字列としてreturnするので、
	 *         メソッドの戻り値の型はString型
	 * 
	 */
	@RequestMapping(value = "/syouyo/ichiran", params = "SUBMIT_SEARCH", method = RequestMethod.POST)
	/*
	 *  ②コントロール処理:引数で渡ってきたKKS0006_F_001Modelは
	 *   Model内のKKS0006_F_001Modelとインスタンスが同じため、KKS0006_F_001Modelを操作すると
	 *   Model内のKKS0006_F_001Modelの値も変更されます。
	 *   returnとして、View名をStringで返しています。「@ModelAttribute("KKS0006_F_001Model")KKS0006_F_001Model
	 *   kks0006_F_001Model」⇒model（画面DTO）に紐づける
	 */
	public String search(@Valid @ModelAttribute("KKS0006_F_001Model") KKS0006_F_001Model search, BindingResult result,
			Model model, HttpSession session) {

		// セッションクラスの宣言（インスタンス生成）
		// 検索処理で取得したデータを格納するセッションの宣言
		KKS0006_S_001Model ses = new KKS0006_S_001Model();
		// 検索処理のサービスクラスを呼び出す
		// TODO 変数名見直し
		serService.search(search, result);
		// 検索処理後初期表示サービスを呼ぶ
		initService.init(search);
		// 画面表示
		// controllerクラスの値（引数）を渡す
		// TODO 変数名の見直し
		model.addAttribute("KKS0006_F_001Model", search);
		// TODO 登録・追加された際に反映されないのでは？処理を考える
		// 検索結果の全件分をセッションに保存
		ses.setIchiran(search);
		// sessionスコープへ格納
		session.setAttribute("KKS0006_S_001", ses);
		return "syouyo_ichiran";
	}

	// 登録ボタン

	/**
	 * 登録ボタン押下後詳細画面へ遷移する（セッションに保存された情報は削除する）処理メソッド
	 * 
	 * @author 脇本
	 * @param HttpSession
	 *            session セッション管理（引数をsessionとしている）
	 * @return 遷移先のテンプレート名(redirect:/syouyo/syousai)を文字列としてreturnするので、
	 *         メソッドの戻り値の型はString型
	 */
	@RequestMapping(value = "/syouyo/ichiran", params = "SUBMIT_REGISTER", method = RequestMethod.POST)
	public String touroku(HttpSession session) {

		// 登録ボタンによる画面遷移（詳細画面へ） 一覧画面情報の検索処理によって取得した情報を保持するセッションデータを削除する
		session.removeAttribute("KKS0006_S_001");
		// 一覧画面の詳細ボタンにて詳細画面へ送る（社員コード、支給年月、行番号）を詰めたセッションデータを削除する
		session.removeAttribute("KKS0006_S_002");
		// session.removeAttribute("employeeCd");
		// session.removeAttribute("inputPeriod");
		// session.removeAttribute("RN");

		// 詳細画面へリダイレクト(画面遷移させる)「サーバが[/syouyo/syousai]を要求する指示をクライアントに戻す」
		return "redirect:/syouyo/syousai";
	}

	// 削除ボタン
	// 削除処理を行うサービスクラス"deleteService"
	@Autowired
	private KKS0006_F_001DeleteSer deleteService;

	/**
	 * 検索処理後、検索結果の表示項目にチェックボックスが表示されている
	 * そのチェックボックスにチェックがついているレコード(複数選択可)を削除し、削除した結果を反映させた画面を表示するメソッド
	 * 
	 * @author 脇本
	 * @param KKS0006_F_001Model
	 *            kks0006_F_001Model
	 *            画面DTO(KKS0006_F_001Model)であり、画面に表示されている項目すべてを格納する。
	 * @param BindingResult
	 *            result 入力チェック処理をサービスクラス(deleteService)で行うので、「BindingResult
	 *            result」をサービスクラスへ渡す。
	 * @param Model
	 *            model
	 *            テンプレートから参照する変数名:KKS0006_F_001Model,オブジェクト名:initService.init(
	 *            search)
	 * @param HttpSession
	 *            session セッション管理（引数をsessionとしている）
	 * @return 遷移先のテンプレート名(syouyo_ichiran)を文字列としてreturnするので、メソッドの戻り値の型はString型
	 * 
	 */

	@RequestMapping(value = "/syouyo/ichiran", params = "SUBMIT_DELETE", method = RequestMethod.POST)
	public String delete(@Valid @ModelAttribute("KKS0006_F_001Model") KKS0006_F_001Model kks0006_F_001Model,
			BindingResult result, Model model, HttpSession session) {

		// 削除処理を行うサービスクラスを呼び出す deleteServiceではDBの更新を行うだけ
		deleteService.delete(kks0006_F_001Model);
		// 削除処理後に再検索をする
		KKS0006_F_001Model search = serService.search(kks0006_F_001Model, result);
		// 再検索した結果で初期表示を行う
		KKS0006_F_001Model delete = initService.init(search);
		//画面表示 addAttribute()メソッドでView側へ渡すオブジェクトのデータを第一引数にテンプレートから参照する変数名[
		//KKS0006_F_001Model]、第二引数にオブジェクト名[fm]として格納
		//削除処理→検索処理→初期表示を行った結果を詰めたオブジェクト(fm)のデータをView側へ渡す
		model.addAttribute("KKS0006_F_001Model", delete);

		return "syouyo_ichiran";
	}

	// メニューボタン

	/**
	 * ボタン押下後、メニュー画面へ遷移する（セッションに保存された情報は削除する）処理メソッド
	 * 
	 * @author 脇本
	 * @param HttpSession
	 *            session セッション管理（引数をsessionとしている）
	 * @return 遷移先のテンプレート名(redirect:/)を文字列としてreturnするので、メソッドの戻り値の型はString型
	 */

	@RequestMapping(value = "/syouyo/ichiran", params = "SUBMIT_MENU", method = RequestMethod.POST)
	public String menu(HttpSession session) {

		//メニューボタンによる画面遷移（メニュー画面へ） 一覧画面情報の検索処理によって取得した情報を保持するセッションデータを削除する
		session.removeAttribute("KKS0006_S_001");
		// 一覧画面の詳細ボタンにて詳細画面へ送る（社員コード、支給年月、行番号）を詰めたセッションデータを削除する
		session.removeAttribute("KKS0006_S_002");
		// session.removeAttribute("employeeCd");
		// session.removeAttribute("inputPeriod");
		// session.removeAttribute("RN");

		return "redirect:/";
	}

	/**
	 * <<(前表示)ボタン
	 * 
	 * @param KKS0006_F_001Model
	 *            kks0006_F_001Model 画面に表示する項目を格納するDTO
	 * @param model
	 *            テンプレートから参照する変数名:KKS0006_F_001Model,オブジェクト名:fmodel(
	 *            前ページ処理結果と初期表示処理結果を詰めたもの)
	 * @param session
	 *            セッション管理
	 * @return"syouyo_ichiran" 遷移先（賞与情報一覧画面）のテンプレート名(syouyo_ichiran)
	 *                         を文字列としてreturnするので、メソッドの戻り値の型はString型
	 */

	// 前ページ処理を行うサービスクラス
	@Autowired
	private KKS0006_F_001PageBackSer pageBack;

	@RequestMapping(value = "/syouyo/ichiran", params = "BUTTON_BACK", method = RequestMethod.POST)
	public String pageBack(@Valid @ModelAttribute("KKS0006_F_001Model") KKS0006_F_001Model pageback, Model model,
			HttpSession session) {

		// 前ページ処理サービス（KKS0006_F_001PageBackSer backPage）を呼び出す
		pageBack.pageBack(pageback);
		//初期表示サービスを呼び出す
		initService.init(pageback);
		// 画面表示
		model.addAttribute("KKS0006_F_001Model", pageback);

		return "syouyo_ichiran";
	}

	/**
	 * >>(次表示)ボタン
	 * 
	 * @param kks0006_F_001Model
	 *            (引数) 画面に表示する項目を格納するDTO
	 * @param model（引数）
	 *            テンプレートから参照する変数名:KKS0006_F_001Model,オブジェクト名:fmodel(
	 *            前ページ処理結果と初期表示処理結果を詰めたもの)
	 * @param session
	 *            セッション管理
	 * @return"syouyo_ichiran" 遷移先（賞与情報一覧画面）のテンプレート名(syouyo_ichiran)
	 *                         を文字列としてreturnするので、メソッドの戻り値の型はString型
	 */

	// 次ページ処理を行うサービスクラス
	@Autowired
	private KKS0006_F_001PageUpSer upPage;

	@RequestMapping(value = "/syouyo/ichiran", params = "BUTTON_NEXT", method = RequestMethod.POST)
	public String pageUp(@Valid @ModelAttribute("KKS0006_F_001Model") KKS0006_F_001Model pageup, Model model,
			HttpSession session) {

		// 次ページ処理を行うサービスクラスを呼び出す
		upPage.pageUp(pageup);
		// ページ処理後、初期表示処理を行う
		initService.init(pageup);
		// 画面表示
		model.addAttribute("KKS0006_F_001Model", pageup);

		return "syouyo_ichiran";
	}

	/**
	 * 詳細ボタン
	 * 
	 * @param form
	 *            画面に表示する項目を格納するDTO
	 * @param result
	 *            入力チェック処理をサービスクラス(serService)で行うので、「BindingResult
	 *            result」をサービスクラスへ渡す
	 * @param Model
	 *            model
	 * @param session
	 *            社員コード,支給年月をセッションに詰める
	 * @return "redirect:/syouyo/syousai"
	 *         遷移先のテンプレート名(redirect:/syouyo/syousai)を文字列としてreturnするので、
	 *         メソッドの戻り値の型はString型
	 */

	@RequestMapping(value = "/syouyo/ichiran", params = "SUBMIT_DETAIL", method = RequestMethod.POST)
	public String syousai(@ModelAttribute("KKS0006_F_001") KKS0006_F_001Model form, HttpSession session) {

		// 詳細画面へ送りたい値を格納するセッション用のクラスを宣言
		KKS0006_S_002Model syousai = new KKS0006_S_002Model();

		/*
		 * セッション（KKS0006_S_002Model型 KKS0006_S_002）に詳細画面へ渡したい値を詰める
		 * 社員コード、支給年月をセッションに詰める（詳細画面の初期表示処理、ページ処理に使う）
		 */
		syousai.setEmployeeCd(form.getSelectedMemberCd());
		// 支給年月をセッションに詰める（詳細画面の初期表示処理、ページ処理に使う）
		syousai.setInputPeriod(form.getSelectedSupplyYm());
		// 行番号をセッションに詰める（詳細画面のページ処理に使う）
		syousai.setRN(form.getSelectedRowNum());
		// セッションへ明細項目の社員コード,支給年月、行番号を保存
		session.setAttribute("KKS0006_S_002", syousai);

		// 詳細画面へリダイレクト(画面遷移させる)「サーバが[/syouyo/syousai]を要求する指示をクライアントに戻す」
		return "redirect:/syouyo/syousai";
	}

}
